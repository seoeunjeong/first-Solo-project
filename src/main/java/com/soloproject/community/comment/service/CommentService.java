package com.soloproject.community.comment.service;

import com.soloproject.community.Exception.BusinessLogicException;
import com.soloproject.community.Exception.ExceptionCode;
import com.soloproject.community.article.repository.ArticleRepository;
import com.soloproject.community.article.service.ArticleService;
import com.soloproject.community.comment.entity.Comment;
import com.soloproject.community.comment.repository.CommentRepository;
import com.soloproject.community.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final MemberService memberService;
    private final ArticleService articleService;
    private final CommentRepository commentRepository;

    public Comment createComment(Comment comment) {
        memberService.findVerifiedMember(comment.getMember().getMemberId());
        articleService.findVerifiedArticle(comment.getArticle().getArticleId());

        return commentRepository.save(comment);
    }

    public Comment updateComment(Comment comment) {
        Comment findComment = findVerifiedComment(comment.getCommentId());
        Optional.ofNullable(comment.getContent()).ifPresent(content -> findComment.setContent(content));

        return commentRepository.save(findComment);
    }

    public Comment findComment(Long commentId){
        return findVerifiedComment(commentId);
    }

    public Comment findVerifiedComment(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Comment findComment =
                optionalComment.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        return findComment;
    }

    public List<Comment> findAllComments(Long articleId) {
        articleService.findVerifiedArticle(articleId);
        return commentRepository.findAllByArticle_articleId(articleId);
    }
}
