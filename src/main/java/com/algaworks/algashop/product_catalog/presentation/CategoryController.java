package com.algaworks.algashop.product_catalog.presentation;

import com.algaworks.algashop.product_catalog.application.category.management.CategoryInput;
import com.algaworks.algashop.product_catalog.application.category.management.CategoryManagementService;
import com.algaworks.algashop.product_catalog.application.category.query.CategoryDetailOutput;
import com.algaworks.algashop.product_catalog.application.category.query.CategoryQueryService;
import com.algaworks.algashop.product_catalog.application.product.query.PageModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryQueryService categoryQueryService;
    private final CategoryManagementService categoryManagementService;

    @GetMapping
    public PageModel<CategoryDetailOutput> filter(
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page
    ) {
        return categoryQueryService.filter(size, page);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDetailOutput create(@RequestBody @Valid CategoryInput input) {
        var categoryId = categoryManagementService.create(input);
        return categoryQueryService.findById(categoryId);
    }

    @GetMapping("/{categoryId}")
    public CategoryDetailOutput findById(@PathVariable("categoryId") UUID categoryId) {
        return categoryQueryService.findById(categoryId);
    }

    @PutMapping("/{categoryId}")
    public CategoryDetailOutput update(@PathVariable("categoryId") UUID categoryId,
                                       @RequestBody @Valid CategoryInput input) {
        categoryManagementService.update(categoryId, input);
        return categoryQueryService.findById(categoryId);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disable(@PathVariable("categoryId") UUID categoryId) {
        categoryManagementService.disable(categoryId);
    }
}
