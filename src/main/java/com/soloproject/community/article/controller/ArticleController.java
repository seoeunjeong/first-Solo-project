package com.soloproject.community.article.controller;


import com.soloproject.community.article.dto.ArticleDto;
import com.soloproject.community.article.entity.Article;
import com.soloproject.community.article.mapper.ArticleMapper;
import com.soloproject.community.article.repository.ArticleRepository;
import com.soloproject.community.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final ArticleMapper mapper;
    private final ArticleRepository articleRepository;

    @PostMapping("/post")
    public ResponseEntity<?> postArticle(@RequestBody ArticleDto.Post postArticleDto) {

        Article article = mapper.articlePostDtoToArticle(postArticleDto);
        Article createdArticle = articleService.createArticle(article);

        return new ResponseEntity<>(mapper.articleToArticleResponseDto(createdArticle), HttpStatus.CREATED);
    }

    @PatchMapping("/{article-id}")
    public ResponseEntity<?> updateArticle(@PathVariable("article-id") Long articleId,
                                        @RequestBody ArticleDto.Patch patchArticleDto) {

        patchArticleDto.setArticleId(articleId);
        Article article = mapper.articlePatchDtoToArticle(patchArticleDto);

        Article updateArticle = articleService.updateArticle(article);

        return new ResponseEntity<>(mapper.articleToArticleResponseDto(updateArticle), HttpStatus.OK);
    }

    @GetMapping("/find/{article-id}")
    public ResponseEntity<?> findArticle(@PathVariable("article-id") Long articleId) {
        Article findArticle = articleService.findArticle(articleId);
        return new ResponseEntity<>(mapper.articleToArticleResponseDto(findArticle), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<?> findArticle() {
        List<Article> articleList = articleService.findArticles();
        return new ResponseEntity<>(mapper.articlesToArticleResponseDtos(articleList), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteArticle(){
        articleService.deleteArticles();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

  /*  @GetMapping("/{search}")
    public ResponseEntity<?> searchArticle(@RequestParam(defaultValue = "0")int page,
                                       @RequestParam(defaultValue = "20")int size,
                                       @RequestParam String keyword){
        if(keyword==null||keyword.trim().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("키워드를 입력하세요");
        }
        PageRequest pageRequest= PageRequest.of(page,size);
        Page<Article> articles= articleRepository.search(keyword,pageRequest);
        return ResponseEntity.ok().body(articles);
    }*/
}
