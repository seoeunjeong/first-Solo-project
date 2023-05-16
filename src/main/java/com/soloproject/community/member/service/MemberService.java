package com.soloproject.community.member.service;

import com.soloproject.community.Exception.BusinessLogicException;
import com.soloproject.community.Exception.ExceptionCode;
import com.soloproject.community.member.entity.Member;
import com.soloproject.community.member.repository.MemberRepository;
import com.soloproject.community.security.CustomAuthorityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final CustomAuthorityUtils authorityUtils;
    private final PasswordEncoder passwordEncoder;

    //private final AwsS3Service awsS3Service;
    //회원가입로직//
    public Member createMember(Member member) {

        verifyExistsEmail(member.getEmail());

        String encryptedPassword = passwordEncoder.encode(member.getPassword());

        member.setPassword(encryptedPassword);

        List<String> roles = authorityUtils.createRoles(member.getEmail());

        member.setRoles(roles);

//         이미지 따로 떼기..ㅎㅎ
//        awsS3Service.upload(profileImage,"profileImage");
//        member.setProfileImageURL(awsS3Service.upload(profileImage,"profileImage"));

        return memberRepository.save(member);
    }

    public Member updateMember(Member member) {

        Member updateMember = findVerifiedMember(member.getMemberId());

        Optional.ofNullable(member.getNickname()).ifPresent(nickname -> updateMember.setNickname(nickname));
        Optional.ofNullable(member.getGender()).ifPresent(gender -> updateMember.setGender(gender));
        Optional.ofNullable(member.getAge()).ifPresent(age -> updateMember.setAge(age));
        Optional.ofNullable(member.getAddress()).ifPresent(address -> updateMember.setAddress(address));

        return memberRepository.save(updateMember);
    }

    public Member findMember(long memberId) {

        return findVerifiedMember(memberId);
    }

    public Page<Member> findMembers(int page, int size) {

        return memberRepository.findAll(PageRequest.of(page, size, Sort.by("memberId").descending()));
    }


    public void adminDeleteMember(long memberId) {
        memberRepository.deleteById(memberId);
    }


    public void adminDeleteMembers() {
        memberRepository.deleteAll();
    }

    public void userDeleteMember(long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        findMember.setMemberStatus(Member.MemberStatus.MEMBER_QUIT);
        memberRepository.save(findMember);
    }

    public Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember= optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }

    private void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
    }

}
