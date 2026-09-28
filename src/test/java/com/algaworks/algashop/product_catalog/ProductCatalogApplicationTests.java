package com.algaworks.algashop.product_catalog;

import com.algaworks.algashop.product_catalog.application.product.management.ProductManagementApplicationService;
import com.algaworks.algashop.product_catalog.application.product.query.ProductQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class ProductCatalogApplicationTests {

	@MockitoBean
	private ProductQueryService productQueryService;

	@MockitoBean
	private ProductManagementApplicationService productManagementApplicationService;

	@Test
	void contextLoads() {
	}

}
