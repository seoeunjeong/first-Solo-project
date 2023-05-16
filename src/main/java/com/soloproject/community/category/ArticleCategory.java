package com.soloproject.community.category;

import com.soloproject.community.article.entity.Article;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class ArticleCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(unique = true)
    private String name;

    //하위카테고리//

    @OneToMany(mappedBy = "articleCategory")
    private List<Article> articleList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private ArticleCategory parent;

    @OneToMany(mappedBy = "parent")
    private List<ArticleCategory> child = new ArrayList<>();

    //연관관계메소드작성//
    public void addChildCategory(ArticleCategory child) {
        this.child.add(child);
        child.setParent(this);
    }

}
