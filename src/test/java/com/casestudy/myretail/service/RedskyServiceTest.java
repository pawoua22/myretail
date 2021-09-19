package com.casestudy.myretail.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class RedskyServiceTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    @Spy
    RedskyService service = new RedskyService();

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(service, "productInfoPath", "http://localhost:8080/redsky/product/{id}");
    }

    @Test
    public void testGetRedskyItemInfo() {

        ItemModel.Product.Item.Description desc = new ItemModel.Product.Item.Description();
        desc.setTitle("Product 123456");
        ItemModel.Product.Item item = new ItemModel.Product.Item();
        item.setTcin("123456");
        item.setProduct_description(desc);
        ItemModel.Product itemProduct = new ItemModel.Product();
        itemProduct.setItem(item);
        ItemModel itemModel = new ItemModel();
        itemModel.setProduct(itemProduct);

        when(restTemplate.getForEntity(anyString(), any(), anyString()))
                .thenReturn(new ResponseEntity(itemModel, HttpStatus.OK));
        ItemModel response = service.getItemInfo("123456");

        assertEquals(itemModel.getProductId(), response.getProductId());
        assertEquals(itemModel.getProductName(), response.getProductName());
    }

}
