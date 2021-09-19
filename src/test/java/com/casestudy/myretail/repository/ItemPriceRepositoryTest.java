package com.casestudy.myretail.repository;

import com.casestudy.myretail.domain.ItemPriceModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ItemPriceRepositoryTest {

    @Autowired
    ItemPriceRepository repo;

    @AfterEach
    void cleanUp() {
        repo.deleteAll();
    }

    @BeforeEach
    public void setRepo(){
        ItemPriceModel itemPriceModel1 = new ItemPriceModel(13860428, new BigDecimal(13.49), "USD");
        ItemPriceModel itemPriceModel2 = new ItemPriceModel(13860428, new BigDecimal(14.27), "CAD");
        ItemPriceModel itemPriceModel3 = new ItemPriceModel(13264003, new BigDecimal(0.99), "USD");
        repo.deleteAll(); // ensure clean up if tests interrupted
        repo.saveAll(Arrays.asList(itemPriceModel1, itemPriceModel2, itemPriceModel3));
    }


    @Test
    public void testGetByItemAndUSD() {
        Optional<ItemPriceModel> itemPriceModel = repo.findByItemIdAndCurrency(13860428, "USD");
        assertTrue(itemPriceModel.isPresent());
        assertEquals(itemPriceModel.get().getItemId(), 13860428);
        assertEquals(itemPriceModel.get().getCurrency(), "USD");
    }

    @Test
    public void testGetByItemAndUSD_duplicate() {
        ItemPriceModel insertItemPrice = new ItemPriceModel(13860428, new BigDecimal(13.49), "USD");
        repo.save(insertItemPrice);
        assertThrows(IncorrectResultSizeDataAccessException.class,
                () -> repo.findByItemIdAndCurrency(13860428, "USD")
        );

    }

}
