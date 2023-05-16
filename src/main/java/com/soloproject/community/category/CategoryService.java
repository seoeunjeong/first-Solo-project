package com.soloproject.community.category;

import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final ArticleCategoryRepository categoryRepository;

    public CategoryService(ArticleCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public ArticleCategory createParentCategory(CategoryDto categoryDto) {
        ArticleCategory ParentCategory = new ArticleCategory();
        ParentCategory.setName(categoryDto.getName());

        ArticleCategory savedCategory = categoryRepository.save(ParentCategory);

        return savedCategory;
    }

    public ArticleCategory createChildCategory(CategoryDto categoryDto) {
        ArticleCategory parentCategory = categoryRepository.findById(categoryDto.getParentId())
                .orElseThrow(() -> new IllegalArgumentException("부모 카테고리를 찾을 수 없습니다."));

        ArticleCategory childCategory = new ArticleCategory();
        childCategory.setName(categoryDto.getName());
        childCategory.setParent(parentCategory);

        ArticleCategory savedCategory = categoryRepository.save(childCategory);

        return savedCategory;
    }

}
