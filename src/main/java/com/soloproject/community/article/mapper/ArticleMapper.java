package com.soloproject.community.article.mapper;

import com.soloproject.community.article.dto.ArticleDto;
import com.soloproject.community.article.entity.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    @Mapping(source ="memberId",target = "member.memberId")
    Article articlePostDtoToArticle(ArticleDto.Post postArticleDto);
    Article articlePatchDtoToArticle(ArticleDto.Patch patchArticleDto);
    @Mapping(source ="member.memberId",target = "memberId")
    ArticleDto.Response articleToArticleResponseDto(Article article);
    @Mapping(source ="member.memberId",target = "memberId")
    List<ArticleDto.Response> articlesToArticleResponseDtos(List<Article> articleList);


}
