package com.soloproject.community.comment.mapper;

import com.soloproject.community.comment.dto.CommentDto;
import com.soloproject.community.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "memberId",target = "member.memberId")
    @Mapping(source = "articleId",target = "article.articleId")
    Comment commentPostDtoToComment(CommentDto.Post postCommentDto);

    Comment commentUpdateDtoToComment(CommentDto.Update updateCommentDto);
    @Mapping(source = "member.memberId",target = "memberId")
    @Mapping(source = "article.articleId",target="articleId")
    CommentDto.Response commentToCommentResponseDto(Comment comment);

    @Mapping(source = "member.memberId", target = "memberId")
    @Mapping(source = "article.articleId", target = "articleId")
    List<CommentDto.Response> commentsToCommentResponseDtos(List<Comment> commentList);

}
