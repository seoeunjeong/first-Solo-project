package com.soloproject.community.category;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class ArticleCategoryController {
    private final CategoryService categoryService;


    @PostMapping("/parent")
    public ResponseEntity createParentCategory(@RequestBody CategoryDto categoryDto) {
        ArticleCategory createdCategory = categoryService.createParentCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory.getName());
    }

    @PostMapping("/child")
    public ResponseEntity createChildCategory(@RequestBody CategoryDto categoryDto) {
        ArticleCategory createdCategory = categoryService.createChildCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory.getName());
    }

}
