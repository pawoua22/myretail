package com.casestudy.myretail.service;

import lombok.Data;

@Data
public class ItemModel {

    private Product product;

    @Data
    public static class Product {

        private Item item;

        @Data
        public static class Item {
            private String tcin;
            private Description product_description;

            @Data
            public static class Description {
                private String title;
            }

            public String getName() {
                return this.product_description != null ? this.getProduct_description().title : "";
            }

        }
    }

    public String getProductName() {
        return product != null && product.getItem() != null ? product.getItem().getName() : null;
    }


    public String getProductId() {
        return product != null && product.getItem() != null ? product.getItem().getTcin() : null;
    }
}
