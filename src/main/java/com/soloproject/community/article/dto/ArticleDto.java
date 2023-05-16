package com.soloproject.community.article.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ArticleDto {

    @Getter
    @Setter
    public static class Post {

        private Long memberId;

        private String title;

        private String content;

        //카테고리등록추가//

    }

    @Getter
    @Setter
    public static class Patch{

        private Long articleId;

        private String title;

        private String content;
    }

    @Getter
    @Setter
    public static class Response{

        private Long articleId;

        private Long memberId;

        private String title;

        private String content;

        private LocalDateTime createdAt;

        private LocalDateTime modifiedAt;

    }

}
