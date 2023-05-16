package com.soloproject.community.member.entity;

import com.soloproject.community.Date;
import com.soloproject.community.article.entity.Article;
import com.soloproject.community.comment.entity.Comment;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member extends Date {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;//(long타입/Long타입구분)

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    private int age;

    private String gender;

    private String address;

    private String profileImageURL;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_JOIN;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();


    @OneToMany(mappedBy = "member")
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> Comments = new ArrayList<>();


//    //프리때 엉뚱한 시간이 들어가서 시도해본 코드
//    @CreatedDate
//    @Column(name = "created_at", updatable = false)
//    private ZonedDateTime created_At = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

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
