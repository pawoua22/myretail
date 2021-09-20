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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.Min;
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
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updateProduct(@PathVariable int id,
                                                 @RequestBody @Valid Product product,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().toString());
        }
        BigDecimal price = product.getCurrent_price().getValue();
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Price must be equal to or greater than $0.00");
        }
        ItemModel redskyProduct = redskyService.getItemInfo(Integer.toString(id));
        if (redskyProduct == null || StringUtils.isEmpty(redskyProduct.getProductId())) {
            throw new NotFoundException("Product "+id+" not found");
        }

        ItemPriceModel itemPriceModel = itemPriceRepository.findByItemIdAndCurrency(id, "USD")
                .orElse(new ItemPriceModel(id, price,"USD"));
        if (itemPriceModel.get_id() != null) {
            log.info("Updating product {} price from ${} -> ${}", id, itemPriceModel.getValue(), price);
            itemPriceModel.setValue(price);
        } else {
            log.info("Adding new item price {}", itemPriceModel);
        }
        itemPriceRepository.save(itemPriceModel);
        return ResponseEntity.ok().body(new Product(redskyProduct.getProductId(),
                                            redskyProduct.getProductName(),
                                            new ItemPrice(itemPriceModel)));
    }
}
