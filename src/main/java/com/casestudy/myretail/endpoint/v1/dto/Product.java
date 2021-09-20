package com.casestudy.myretail.endpoint.v1.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class Product implements Serializable {

    private int id;
    private String name;
    @Valid
    @NotNull(message = "price required")
    private ItemPrice current_price;

    public Product(String product_id, String title, ItemPrice itemPrice) {
        this.id = Integer.valueOf(product_id).intValue();
        this.name = title;
        this.current_price = itemPrice;
    }

    public Product() {
    }
}
