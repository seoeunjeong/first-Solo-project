package com.soloproject.community.profileimage;

import com.soloproject.community.member.entity.Member;
import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProfileImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String fileUrl;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

}
