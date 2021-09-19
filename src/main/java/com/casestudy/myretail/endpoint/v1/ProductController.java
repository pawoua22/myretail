package com.casestudy.myretail.endpoint.v1;

import com.casestudy.myretail.domain.ItemPriceModel;
import com.casestudy.myretail.endpoint.v1.dto.ItemPrice;
import com.casestudy.myretail.endpoint.v1.dto.Product;
import com.casestudy.myretail.exception.NotFoundException;
import com.casestudy.myretail.repository.ItemPriceRepository;
import com.casestudy.myretail.service.ItemModel;
import com.casestudy.myretail.service.RedskyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private RedskyService redskyService;

    @Autowired
    private ItemPriceRepository itemPriceRepository;

    @Operation(description = "Returns product name and current USD price by id")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProduct(@PathVariable @Min(1) int id) {

        ItemModel redskyProduct = redskyService.getItemInfo(Integer.toString(id));
        if (redskyProduct == null || StringUtils.isEmpty(redskyProduct.getProductId())) {
            throw new NotFoundException("Product "+id+" not found");
        }

        ItemPriceModel price = itemPriceRepository.findByItemIdAndCurrency(id, "USD").orElse(null);
        if (price == null) {
            log.info("No USD price found for {}", id);
        }
        var product = new Product(redskyProduct.getProductId(),
                                      redskyProduct.getProductName(),
                                      new ItemPrice(price));
        return ResponseEntity.ok(product);
    }

    @Operation(description = "Update a product current USD price")
    @PutMapping(value = "/{id}/price", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updateProduct(@PathVariable @Min(1) int id, @RequestBody @NotNull BigDecimal price) {

        return ResponseEntity.ok().body(null);
    }
}
