package com.casestudy.myretail.endpoint.v1.dto;

import lombok.Data;

@Data
public class Product {

    private int id;
    private String name;
    private ItemPrice current_price;

    public Product(String product_id, String title, ItemPrice itemPrice) {
        this.id = Integer.valueOf(product_id).intValue();
        this.name = title;
        this.current_price = itemPrice;
    }
}

