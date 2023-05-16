package com.soloproject.community.comment.controller;

import com.soloproject.community.comment.dto.CommentDto;
import com.soloproject.community.comment.entity.Comment;
import com.soloproject.community.comment.mapper.CommentMapper;
import com.soloproject.community.comment.service.CommentService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentMapper mapper;
    private final CommentService commentService;

    @PostMapping("/{article-id}")
    public ResponseEntity<?> postComment(@PathVariable("article-id") Long articleId,
                                      @RequestBody CommentDto.Post postCommentDto) {
        postCommentDto.setArticleId(articleId);
        Comment comment = mapper.commentPostDtoToComment(postCommentDto);
        Comment createdComment = commentService.createComment(comment);

        return new ResponseEntity<>(mapper.commentToCommentResponseDto(createdComment), HttpStatus.CREATED);
    }

    @PatchMapping("/update/{comment-id}")
    private ResponseEntity<?> updateComment(@PathVariable("comment-id") Long commentId,
                                         @Valid @RequestBody CommentDto.Update updateCommentDto) {

        updateCommentDto.setCommentId(commentId);
        Comment comment = mapper.commentUpdateDtoToComment(updateCommentDto);
        Comment updateComment = commentService.updateComment(comment);
        return new ResponseEntity<>(mapper.commentToCommentResponseDto(updateComment), HttpStatus.OK);
    }

    @GetMapping("/find/{comment-id}")
    private ResponseEntity<?> findComment(@PathVariable("comment-id") Long commentId) {
        Comment findComment = commentService.findComment(commentId);

        return new ResponseEntity<>(mapper.commentToCommentResponseDto(findComment), HttpStatus.OK);
    }

    @GetMapping("/comments/{article-id}")
    private ResponseEntity<?> findComments(@PathVariable("article-id") Long articleId){
        List<Comment> commentList=commentService.findAllComments(articleId);

        return new ResponseEntity<>(mapper.commentsToCommentResponseDtos(commentList),HttpStatus.OK);
    }

}
