package com.casestudy.myretail;

import com.casestudy.myretail.domain.ItemPriceModel;
import com.casestudy.myretail.repository.ItemPriceRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import java.math.BigDecimal;
import java.util.Arrays;

@Slf4j
@EnableMongoRepositories(basePackages = {"com.casestudy.myretail.repository", "com.casestudy.myretail.domain"})
@SpringBootApplication
@OpenAPIDefinition(info =
@Info(title = "MyRetail API", version = "1.0", description = "Proof of Concept", contact =
@Contact(email = "pawoua22@gmail.com")))
public class MyRetailApplication  implements CommandLineRunner {

	@Autowired
	private ItemPriceRepository itemPriceRepository;

	public static void main(String[] args) {
		SpringApplication.run(MyRetailApplication.class, args);
	}

	@Override
	public void run(String... args) {
		// for the purpose of poc
		ItemPriceModel itemPriceModel1 = new ItemPriceModel(13860428, new BigDecimal(13.49), "USD");
		ItemPriceModel itemPriceModel2 = new ItemPriceModel(54456119, new BigDecimal(1.27), "USD");
		ItemPriceModel itemPriceModel3 = new ItemPriceModel(13264003, new BigDecimal(0.99), "USD");
		//ItemPriceModel itemPriceModel4 = new ItemPriceModel(12954218, new BigDecimal(8.10), "USD");

		itemPriceRepository.deleteAll(); // clean start
		itemPriceRepository.saveAll(Arrays.asList(itemPriceModel1, itemPriceModel2, itemPriceModel3));
		log.info("Added seed data to mongodb.");
	}

}
