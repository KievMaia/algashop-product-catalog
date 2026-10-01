package com.algaworks.algashop.product_catalog.contract.base;

import com.algaworks.algashop.product_catalog.application.ResourceNotFoundException;
import com.algaworks.algashop.product_catalog.application.category.management.CategoryInput;
import com.algaworks.algashop.product_catalog.application.category.management.CategoryManagementService;
import com.algaworks.algashop.product_catalog.application.category.query.CategoryDetailOutput;
import com.algaworks.algashop.product_catalog.application.category.query.CategoryDetailOutputTestDataBuilder;
import com.algaworks.algashop.product_catalog.application.category.query.CategoryQueryService;
import com.algaworks.algashop.product_catalog.application.product.query.PageModel;
import com.algaworks.algashop.product_catalog.presentation.CategoryController;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = CategoryController.class)
class CategoryBase {

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private CategoryQueryService categoryQueryService;

    @MockitoBean
    private CategoryManagementService categoryManagementService;

    public static final UUID validCategoryId = UUID.fromString("8f91621b-7661-4cc7-a39a-a3dd7f7e71c4");
    public static final UUID invalidCategoryId = UUID.fromString("d70864cd-671c-4ec2-a3d2-0cc8f5dc55ba");
    public static final UUID createdCategoryId = UUID.fromString("5c9f8a3e-2b17-4d64-8e9a-1f3d5b7c9a21");

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(MockMvcBuilders.webAppContextSetup(context)
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .build());
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();

        mockValidCategoryFindById();
        mockFilterCategories();
        mockCreateCategory();
        mockInvalidCategoryFindById();
        mockUpdateCategory();
        mockDisableCategory();
    }

    private void mockValidCategoryFindById() {
        when(categoryQueryService.findById(validCategoryId))
                .thenReturn(CategoryDetailOutputTestDataBuilder.aCategory()
                        .id(validCategoryId)
                        .build());
    }

    private void mockFilterCategories() {
        when(categoryQueryService.filter(anyInt(), anyInt()))
                .then(answer -> {
                    Integer size = answer.getArgument(0);

                    return PageModel.<CategoryDetailOutput>builder()
                            .number(0)
                            .size(size)
                            .totalPages(1)
                            .totalElements(2)
                            .content(
                                    List.of(
                                            CategoryDetailOutputTestDataBuilder.aCategory().build(),
                                            CategoryDetailOutputTestDataBuilder.aCategoryAlt1().build()
                                    )
                            ).build();
                });
    }

    private void mockCreateCategory() {
        when(categoryManagementService.create(any(CategoryInput.class)))
                .thenReturn(createdCategoryId);
        when(categoryQueryService.findById(createdCategoryId))
                .thenReturn(CategoryDetailOutputTestDataBuilder.aCategory().build());
    }

    private void mockInvalidCategoryFindById() {
        when(categoryQueryService.findById(invalidCategoryId))
                .thenThrow(new ResourceNotFoundException());
    }

    private void mockUpdateCategory() {
        doThrow(new ResourceNotFoundException())
                .when(categoryManagementService)
                .update(eq(invalidCategoryId), any(CategoryInput.class));
    }

    private void mockDisableCategory() {
        doThrow(new ResourceNotFoundException())
                .when(categoryManagementService)
                .disable(invalidCategoryId);
    }
}
