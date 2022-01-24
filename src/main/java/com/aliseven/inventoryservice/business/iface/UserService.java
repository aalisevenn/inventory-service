package com.aliseven.inventoryservice.business.iface;



import com.aliseven.inventoryservice.model.request.CreateUserRequestVO;
import com.aliseven.inventoryservice.model.request.GetCustomerOrderRequestVO;
import com.aliseven.inventoryservice.model.request.LoginRequestVO;
import com.aliseven.inventoryservice.model.response.CreateUserResponseVO;
import com.aliseven.inventoryservice.model.response.CustomerOrderResponseVO;
import com.aliseven.inventoryservice.model.response.LoginResponseVO;

import java.util.List;

public interface UserService {
    CreateUserResponseVO registerUser(CreateUserRequestVO createUserRequest);

    LoginResponseVO authenticateUser(LoginRequestVO loginRequest);

    List<CustomerOrderResponseVO> getCustomerOrders(GetCustomerOrderRequestVO request);
}
