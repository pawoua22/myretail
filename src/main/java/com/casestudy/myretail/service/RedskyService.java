package com.casestudy.myretail.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class RedskyService {

   @Autowired
   private RestTemplate restTemplate;

   @Value("${redsky.url.product.path}")
   private String productInfoPath;

   public ItemModel getItemInfo(String id) {

      ResponseEntity<ItemModel> response = restTemplate.getForEntity(productInfoPath, ItemModel.class, id);
      if (response != null && HttpStatus.OK == response.getStatusCode()) {
        return response.getBody();
      }
      return null;
   }
}
