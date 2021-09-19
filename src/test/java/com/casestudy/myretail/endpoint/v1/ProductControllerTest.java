package com.casestudy.myretail.endpoint.v1;

import com.casestudy.myretail.domain.ItemPriceModel;
import com.casestudy.myretail.repository.ItemPriceRepository;
import com.casestudy.myretail.service.ItemModel;
import com.casestudy.myretail.service.RedskyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc()
@SpringBootTest()
public class ProductControllerTest {

    @MockBean
    RedskyService redskyService;

    @MockBean
    ItemPriceRepository itemPriceRepository;

    @Autowired
    MockMvc mockMvc;

    private static final String API_PRODUCT_PATH = "/api/v1/product";
    private ItemModel itemModel;
    private ItemPriceModel itemPriceModel;

    @BeforeEach
    public void setUp(){
        ItemModel.Product.Item.Description desc = new ItemModel.Product.Item.Description();
        desc.setTitle("Product 123456");
        ItemModel.Product.Item item = new ItemModel.Product.Item();
        item.setTcin("123456");
        item.setProduct_description(desc);
        ItemModel.Product itemProduct = new ItemModel.Product();
        itemProduct.setItem(item);
        itemModel = new ItemModel();
        itemModel.setProduct(itemProduct);

        itemPriceModel = new ItemPriceModel(123456, new BigDecimal("1.00"), "USD");
    }

    @Test
    public void testProductNotFound() throws Exception {

        when(redskyService.getItemInfo(anyString())).thenReturn(null);
        mockMvc.perform(get(API_PRODUCT_PATH+"/{id}", 1)
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        verify(itemPriceRepository, times(0)).findByItemIdAndCurrency(anyInt(), anyString());
    }

    @Test
    public void testProductFound_HappyPath() throws Exception {

        when(redskyService.getItemInfo(anyString())).thenReturn(itemModel);
        when(itemPriceRepository.findByItemIdAndCurrency(anyInt(), anyString())).thenReturn(Optional.of(itemPriceModel));

        mockMvc.perform(get(API_PRODUCT_PATH+"/{id}", 123456))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(123456))
                .andExpect(jsonPath("$.name").value("Product 123456"))
                .andExpect(jsonPath("$.current_price.value").value(1.00))
                .andExpect(jsonPath("$.current_price.currency_code").value("USD"))
        ;

    }

    @Test
    public void testProductFoundButNoPricingAvailable() throws Exception {
        when(redskyService.getItemInfo(anyString())).thenReturn(itemModel);
        when(itemPriceRepository.findByItemIdAndCurrency(anyInt(), anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get(API_PRODUCT_PATH+"/{id}", 123456))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(123456))
                .andExpect(jsonPath("$.name").value("Product 123456"))
                .andExpect(jsonPath("$.current_price.value").doesNotExist())
                .andExpect(jsonPath("$.current_price.currency_code").doesNotExist())
        ;
    }
}
