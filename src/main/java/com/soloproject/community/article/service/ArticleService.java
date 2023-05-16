package com.soloproject.community.article.service;

import com.soloproject.community.Exception.BusinessLogicException;
import com.soloproject.community.Exception.ExceptionCode;
import com.soloproject.community.article.entity.Article;
import com.soloproject.community.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    public final ArticleRepository articleRepository;

    public Article createArticle(Article article){
        return articleRepository.save(article);

    }
    public Article updateArticle(Article article){

        Article findArticle=findVerifiedArticle(article.getArticleId());
        //수정로직
        Optional.ofNullable(article.getTitle()).ifPresent(title -> findArticle.setTitle(title));
        Optional.ofNullable(article.getContent()).ifPresent(content -> findArticle.setContent(content));

        //트렌젝션없으면 저장해야되는구나
        return articleRepository.save(findArticle);
    }

    public Article findArticle(Long articleId) {
        return findVerifiedArticle(articleId);
    }

    public List<Article> findArticles(){

       List<Article> articleList= articleRepository.findAll();

       return articleList;

    }

    public void deleteArticle(Long articleId){
        articleRepository.deleteById(articleId);
    }


    public void deleteArticles(){
        articleRepository.deleteAll();
    }


    public Article findVerifiedArticle(long articleId) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        Article findArticle=
                optionalArticle.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.ARTICLE_NOT_FOUND));
        return findArticle;
    }




}
