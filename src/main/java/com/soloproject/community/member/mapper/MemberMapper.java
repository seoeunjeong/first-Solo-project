package com.soloproject.community.member.mapper;

import com.soloproject.community.member.dto.MemberDto;
import com.soloproject.community.member.entity.Member;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member memberPostDtoToMember(MemberDto.Post requestBody);
    Member memberPatchDtoToMember(MemberDto.Patch requestBody);
    MemberDto.Response memberToMemberResponseDto(Member member);
    List<MemberDto.Response> membersTomemberResponseDtos(List<Member> findMembers);
}
