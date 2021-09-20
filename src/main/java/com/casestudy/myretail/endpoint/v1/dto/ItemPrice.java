package com.casestudy.myretail.endpoint.v1.dto;

import com.casestudy.myretail.domain.ItemPriceModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ItemPrice implements Serializable {

    @NotNull(message = "decimal value required")
    private BigDecimal value;
    @NotBlank(message = "currency required")
    private String currency_code;

    public ItemPrice(ItemPriceModel itemPrice) {
        if (itemPrice == null) return;
        this.value = itemPrice.getValue();
        this.currency_code = itemPrice.getCurrency();
    }

    public ItemPrice(){

    }
}
