package com.soloproject.community.VaildTest;

import com.soloproject.community.member.dto.MemberDto;
import org.junit.jupiter.api.Test;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PostDtoValidTest {
    @Test
    void validateMember() {
        MemberDto.Post member = new MemberDto.Post();
        member.setEmail("SEO4015@gmail.com");
        member.setPassword("dkssudgktpdy1!!");
        member.setNickname("은정");

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<MemberDto.Post>> violations = validator.validate(member);

        for (ConstraintViolation<MemberDto.Post> cv : new ArrayList<>(violations)) {
            System.out.println(cv);
        }

        assertEquals(0, violations.size());
    }

 /*   @BeforeClass
    public static void beforeClass() {
        Locale.setDefault(Locale.US); // locale 설정에 따라 에러 메시지가 달라진다.
    }*/

}
