package com.soloproject.community.comment.repository;

import com.soloproject.community.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByArticle_articleId(Long articleId);
}
