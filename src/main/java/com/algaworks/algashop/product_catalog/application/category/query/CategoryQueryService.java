package com.algaworks.algashop.product_catalog.application.category.query;

import com.algaworks.algashop.product_catalog.application.product.query.PageModel;

import java.util.UUID;

public interface CategoryQueryService {

    CategoryDetailOutput findById(UUID categoryId);

    PageModel<CategoryDetailOutput> filter(Integer size, Integer page);
}
