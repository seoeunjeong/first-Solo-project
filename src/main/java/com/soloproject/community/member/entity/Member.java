package com.soloproject.community.member.entity;

import com.soloproject.community.audit.Auditable;
import com.soloproject.community.article.entity.Article;
import com.soloproject.community.comment.entity.Comment;
import com.soloproject.community.profileimage.ProfileImage;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    private Integer age;

    private String gender;

    private String address;

    private String profileImageURL;

    @OneToMany(mappedBy ="member")
    private List<ProfileImage> profileImages=new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_JOIN;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();


    @OneToMany(mappedBy = "member")
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> Comments = new ArrayList<>();


    public enum MemberStatus {
        MEMBER_JOIN("회원가입"),
        MEMBER_QUIT("회원탈퇴");
        @Getter
        private final String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }
}
