package com.soloproject.community.comment.entity;

import com.soloproject.community.Date;
import com.soloproject.community.article.entity.Article;
import com.soloproject.community.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment extends Date {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long commentId;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "member_Id")
     private Member member;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "article_id")
     private Article article;

     private String content;

}
