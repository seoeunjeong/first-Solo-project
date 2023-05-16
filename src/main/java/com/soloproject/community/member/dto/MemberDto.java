package com.soloproject.community.member.dto;

import com.soloproject.community.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;


public class MemberDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Post {

        @Email(message = "이메일의 형식이 올바르지 않습니다.")
        @NotBlank(message = "아이디는 필수 입력 값 입니다.")
        private String email;

        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$",
                message = "비밀번호는 영문,특수문자,숫자를 포함하여 8자리 이상이여야합니다.")
        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        private String password;

        //패스워드 입력 확인 필드

        @NotBlank(message = "닉네임은 필수 입력 값 입니다.")
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
        private String nickname;

        public Post(String email, String password, String nickname) {
            this.email = email;
            this.password = password;
            this.nickname = nickname;
        }
    }
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Patch{

        private Long memberId;

        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$",
                message = "비밀번호는 영문,특수문자,숫자를 포함하여 8자리 이상이여야합니다.")
        private String password;

        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,6}$", message = "닉네임은 특수문자를 제외한 2~6자리여야 합니다.")
        private String nickname;

        private int age;

        private String gender;

        private String address;

        private Member.MemberStatus memberStatus;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Response{

        private Long memberId;

        private String email;

        private String nickname;

        private int age;

        private String gender;

        private String address;

        private String profileImageURL;

        private Member.MemberStatus memberStatus;

        private LocalDateTime createdAt;

        private LocalDateTime modifiedAt;

    }
}

