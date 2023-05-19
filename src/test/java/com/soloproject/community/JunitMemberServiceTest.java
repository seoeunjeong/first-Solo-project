package com.soloproject.community;

import com.soloproject.community.Exception.BusinessLogicException;
import com.soloproject.community.member.entity.Member;
import com.soloproject.community.member.repository.MemberRepository;
import com.soloproject.community.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class JunitMemberServiceTest {
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private MemberService memberService;

    @Test
    public void createMemberTest() {
        // given
        Member member = new Member();
        member.setEmail("dmswjd4015@naver.com");
        member.setPassword("dlkssudgktpdy1!");
        member.setNickname("서은정");

        // (4)
        given(memberRepository.findByEmail(Mockito.anyString())).willReturn(Optional.of(member));

        // when / then (6)
        assertThrows(BusinessLogicException.class, () -> memberService.createMember(member));

    }

    @Test
    public void updateMemberTest() {
        Member member = new Member();
        member.setMemberId(1L);

        given(memberRepository.findById(Mockito.anyLong())).willReturn(Optional.empty());

        assertThrows(BusinessLogicException.class, () -> memberService.updateMember(member));
    }
}
