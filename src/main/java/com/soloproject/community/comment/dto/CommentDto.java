package com.soloproject.community.comment.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


public class CommentDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Post {

        @NotBlank(message = "등록하는회원 Id는 필수값입니다.")
        private Long memberId;


        private Long articleId;

        @NotBlank(message = "댓글 내용은 필수값입니다.")
        private String content;


    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Update {

        private Long commentId;

        private String content;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Response {

        private Long commentId;

        private Long memberId;

        private Long articleId;

        private String content;

        private LocalDateTime createdAt;

        private LocalDateTime modifiedAt;

    }
}
