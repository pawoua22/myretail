package com.casestudy.myretail.domain;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Document(collection = "itemprice")
public class ItemPriceModel {

    @Id
    private ObjectId _id;

    @NotNull
    private int itemId;

    @NotNull
    private BigDecimal value;

    @NotNull
    private String currency;

    public ItemPriceModel(int itemId, BigDecimal value, String currency) {
        this.itemId = itemId;
        this.value = value;
        this.currency = currency;
    }

}
