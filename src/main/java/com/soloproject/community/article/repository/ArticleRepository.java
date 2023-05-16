package com.soloproject.community.article.repository;

import com.soloproject.community.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article,Long> {

    @Query(value = "SELECT DISTINCT a FROM Article a WHERE a.title LIKE %:keyword% OR a.content LIKE %:keyword%")
    Page<Article> search(@Param("keyword")String keyword, Pageable pageable);
}
