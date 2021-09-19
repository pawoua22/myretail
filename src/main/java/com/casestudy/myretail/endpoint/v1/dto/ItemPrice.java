package com.casestudy.myretail.endpoint.v1.dto;

import com.casestudy.myretail.domain.ItemPriceModel;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ItemPrice {

    private BigDecimal value;
    private String currency_code;

    public ItemPrice(ItemPriceModel itemPrice) {
        if (itemPrice == null) return;
        this.value = itemPrice.getValue();
        this.currency_code = itemPrice.getCurrency();
    }
}