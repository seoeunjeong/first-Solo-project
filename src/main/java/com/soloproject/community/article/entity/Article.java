package com.soloproject.community.article.entity;

import com.soloproject.community.audit.Auditable;
import com.soloproject.community.category.ArticleCategory;
import com.soloproject.community.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Article extends Auditable {

    @Id @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long articleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ArticleCategory articleCategory;
}
