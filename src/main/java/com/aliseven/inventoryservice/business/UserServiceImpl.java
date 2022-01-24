package com.aliseven.inventoryservice.business;

import com.aliseven.inventoryservice.auth.JwtUtil;
import com.aliseven.inventoryservice.auth.UserDetailsImpl;
import com.aliseven.inventoryservice.business.iface.OrderService;
import com.aliseven.inventoryservice.business.iface.UserService;
import com.aliseven.inventoryservice.exception.BusinessException;
import com.aliseven.inventoryservice.exception.ExceptionConstants;
import com.aliseven.inventoryservice.model.entity.*;
import com.aliseven.inventoryservice.model.repository.RoleRepository;
import com.aliseven.inventoryservice.model.repository.UserRepository;
import com.aliseven.inventoryservice.model.request.CreateUserRequestVO;
import com.aliseven.inventoryservice.model.request.GetCustomerOrderRequestVO;
import com.aliseven.inventoryservice.model.request.LoginRequestVO;
import com.aliseven.inventoryservice.model.response.CreateUserResponseVO;
import com.aliseven.inventoryservice.model.response.CustomerOrderResponseVO;
import com.aliseven.inventoryservice.model.response.LoginResponseVO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final OrderService orderService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder encoder, AuthenticationManager authenticationManager,
                           JwtUtil jwtUtil, OrderService orderService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.orderService = orderService;
    }



    @Override
    public CreateUserResponseVO registerUser(CreateUserRequestVO createUserRequest) {

        if (userRepository.existsByUsername(createUserRequest.getUsername())) {
            throw new BusinessException("Error: Username is already taken", ExceptionConstants.USER_ALREADY_EXISTS);
        }
        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new BusinessException("Error: Email is already in use", ExceptionConstants.EMAIL_ALREADY_EXISTS);
        }
        // Create new user's account
        UserEntity user = UserEntity.builder()
                .username(createUserRequest.getUsername())
                .password(encoder.encode(createUserRequest.getPassword()))
                .email(createUserRequest.getEmail())
                .userType(UserType.valueOf(createUserRequest.getUserType()))
                .build();

        Set<String> requestRoles = createUserRequest.getRoles();
        Set<RoleEntity> roles = this.getRoles(requestRoles);

        user.setUpdateUser("SYSTEM_USER");
        user.setRoles(roles);
        userRepository.save(user);
        return new CreateUserResponseVO("User registered successfully");
    }

    private Set<RoleEntity> getRoles(Set<String> strRoles) {
        Set<RoleEntity> roles = new HashSet<>();

        if (strRoles == null) {
            RoleEntity userRole = roleRepository.findByName(RoleType.ROLE_USER)
                    .orElseThrow(() -> new BusinessException("Error: Role is not found.", ExceptionConstants.USER_ROLE_NOT_FOUND));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        RoleEntity adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
                                .orElseThrow(() -> new BusinessException("Error: Role is not found.", ExceptionConstants.USER_ROLE_NOT_FOUND));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        RoleEntity modRole = roleRepository.findByName(RoleType.ROLE_MODERATOR)
                                .orElseThrow(() -> new BusinessException("Error: Role is not found.", ExceptionConstants.USER_ROLE_NOT_FOUND));
                        roles.add(modRole);

                        break;
                    default:
                        RoleEntity userRole = roleRepository.findByName(RoleType.ROLE_USER)
                                .orElseThrow(() -> new BusinessException("Error: Role is not found.", ExceptionConstants.USER_ROLE_NOT_FOUND));
                        roles.add(userRole);
                }
            });
        }
        return roles;
    }

    @Override
    public LoginResponseVO authenticateUser(LoginRequestVO loginRequest) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BusinessException("Incorrect Username or Password", ExceptionConstants.USER_NOT_FOUND, Collections.singletonList(e.getMessage()));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(loginRequest.getUsername());

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new LoginResponseVO(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    @Override
    public List<CustomerOrderResponseVO> getCustomerOrders(GetCustomerOrderRequestVO request) {
        UserDetailsImpl user = (UserDetailsImpl)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = Optional.ofNullable(request.getUserId()).orElse(user.getId());
        List<OrderEntity> orderList = orderService.findByUserIdAndActive(userId, Boolean.TRUE);
        List<CustomerOrderResponseVO> list = orderList.stream()
                .map(item -> CustomerOrderResponseVO.builder()
                        .products(item.getProducts())
                        .id(item.getId())
                        .quantity(item.getQuantity())
                        .totalAmount(item.getPrice())
                        .userId(item.getUserId()).build())
                .collect(Collectors.toList());

        return list;
    }
}
