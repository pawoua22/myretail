package com.casestudy.myretail.repository;

import com.casestudy.myretail.domain.ItemPriceModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ItemPriceRepository extends MongoRepository<ItemPriceModel, String> {

    Optional<ItemPriceModel> findByItemIdAndCurrency(int itemId, String currency);
}
