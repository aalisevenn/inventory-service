package com.aliseven.inventoryservice.auth;

import com.aliseven.inventoryservice.exception.AuthorizationException;
import com.aliseven.inventoryservice.exception.ExceptionConstants;
import com.aliseven.inventoryservice.model.entity.UserEntity;
import com.aliseven.inventoryservice.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthorizationException("User Not Found with username: " + username, ExceptionConstants.USER_NOT_FOUND));

        return UserDetailsImpl.build(user);
    }

}
