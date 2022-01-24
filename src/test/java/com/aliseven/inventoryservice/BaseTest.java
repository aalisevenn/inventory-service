package com.aliseven.inventoryservice;

import com.aliseven.inventoryservice.business.iface.UserService;
import com.aliseven.inventoryservice.model.entity.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

@SpringBootTest(classes = TestMain.class)
@AutoConfigureMockMvc
@Transactional
public class BaseTest {

    @Autowired
    UserService userService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    protected UserEntity categoryUser;
    protected UserEntity productUser;
    protected UserEntity regionUser;
    protected UserEntity storeUser;
    protected UserEntity stockUser;
    protected UserEntity user;
    protected UserEntity customer1;
    protected UserEntity customer2;
    protected String categoryUserToken;
    protected String productUserToken;
    protected String regionToken;
    protected String storeToken;
    protected String stockToken;
    protected String customer1Token;
    protected String customer2Token;
    protected String token5;
}
