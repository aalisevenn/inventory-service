package com.aliseven.inventoryservice.controller;

import com.aliseven.inventoryservice.BaseTest;
import com.aliseven.inventoryservice.TestMain;
import com.aliseven.inventoryservice.business.iface.CategoryService;
import com.aliseven.inventoryservice.business.iface.ProductService;
import com.aliseven.inventoryservice.model.entity.ProductEntity;
import com.aliseven.inventoryservice.model.repository.ProductRepository;
import com.aliseven.inventoryservice.model.request.CreateProductRequestVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestMain.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class ProductApiTest extends BaseTest {


    @InjectMocks
    private ProductController productController;
    @Mock
    CategoryService categoryService;
    @Mock
    ProductRepository productRepository;
    @Mock
    ProductService productService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    protected String categoryUserToken;

    CreateProductRequestVO createProductRequestVO;
    ProductEntity product;

    @Before
    public void init() {
        createProductRequestVO = createProductRequestVO.builder().name("testName").updatedUser("ali").categoryId(1l)
                .price(BigDecimal.TEN)
                .updatedDate(LocalDateTime.now()).build();

    }

    @Test
    public void productSaveTest() throws Exception {


    }
}
