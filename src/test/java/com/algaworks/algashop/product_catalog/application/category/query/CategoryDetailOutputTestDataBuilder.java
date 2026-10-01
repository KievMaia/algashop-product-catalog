package com.algaworks.algashop.product_catalog.application.category.query;

import java.util.UUID;

public class CategoryDetailOutputTestDataBuilder {

    private CategoryDetailOutputTestDataBuilder() {
    }

    public static CategoryDetailOutput.CategoryDetailOutputBuilder aCategory() {
        return CategoryDetailOutput.builder()
                .id(UUID.randomUUID())
                .name("Notebook")
                .enabled(true);
    }

    public static CategoryDetailOutput.CategoryDetailOutputBuilder aCategoryAlt1() {
        return CategoryDetailOutput.builder()
                .id(UUID.randomUUID())
                .name("Desktop")
                .enabled(true);
    }
}
