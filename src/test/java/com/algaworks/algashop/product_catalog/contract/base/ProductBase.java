package com.algaworks.algashop.product_catalog.contract.base;

import com.algaworks.algashop.product_catalog.application.ResourceNotFoundException;
import com.algaworks.algashop.product_catalog.application.product.management.ProductInput;
import com.algaworks.algashop.product_catalog.application.product.management.ProductManagementApplicationService;
import com.algaworks.algashop.product_catalog.application.product.query.PageModel;
import com.algaworks.algashop.product_catalog.application.product.query.ProductDetailOutput;
import com.algaworks.algashop.product_catalog.application.product.query.ProductDetailOutputTestDataBuilder;
import com.algaworks.algashop.product_catalog.application.product.query.ProductQueryService;
import com.algaworks.algashop.product_catalog.presentation.ProductController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = ProductController.class)
class ProductBase {

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private ProductQueryService productQueryService;

    @MockitoBean
    private ProductManagementApplicationService productManagementApplicationService;

    public static final UUID validProductId = UUID.fromString("019dbc11-088b-7476-8dc0-6cf690b8624b");
    public static final UUID invalidProductId = UUID.fromString("d70864cd-671c-4ec2-a3d2-0cc8f5dc55ba");
    public static final UUID createdProductId = UUID.fromString("7d21f1c6-392f-4a31-ac7b-e927ba1e990e");

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(MockMvcBuilders.webAppContextSetup(context)
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .build());
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();

        mockValidProductFindById();
        mockFilterProducts();
        mockCreateProduct();
        mockInvalidProductFindById();
    }

    private void mockInvalidProductFindById() {
        when(productQueryService.findById(invalidProductId))
                .thenThrow(new ResourceNotFoundException());
    }

    private void mockCreateProduct() {
        when(productManagementApplicationService.create(any(ProductInput.class)))
                .thenReturn(createdProductId);
        when(productQueryService.findById(createdProductId))
                .thenReturn(ProductDetailOutputTestDataBuilder.aProduct().inStock(false).build());
    }

    private void mockFilterProducts() {
        when(productQueryService.filter(anyInt(), anyInt()))
                .then(answer -> {
                    Integer size = answer.getArgument(0);

                    return PageModel.<ProductDetailOutput>builder()
                            .number(0)
                            .size(size)
                            .totalPages(1)
                            .totalElements(2)
                            .content(
                                    List.of(
                                            ProductDetailOutputTestDataBuilder.aProduct().build(),
                                            ProductDetailOutputTestDataBuilder.aProductAlt1().build()
                                    )
                            ).build();
                });
    }

    private void mockValidProductFindById() {
        when(productQueryService.findById(validProductId))
                .thenReturn(ProductDetailOutputTestDataBuilder.aProduct()
                        .id(validProductId)
                        .build());
    }
}
