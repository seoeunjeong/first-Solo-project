package com.soloproject.community;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import com.soloproject.community.member.dto.MemberDto;
import com.soloproject.community.member.entity.Member;
import com.soloproject.community.member.mapper.MemberMapper;
import com.soloproject.community.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(properties = "spring.profiles.active=local")
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class ControllerRestdocTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MemberService memberService;
    @MockBean
    private MemberMapper mapper;
    @Autowired
    private Gson gson;

    @Test
    public void signupMemberTest() throws Exception {
        // given
        MemberDto.Post post = new MemberDto.Post("aaa11111@naver.com", "dkssudgktpdy1!", "서은정");

        given(mapper.memberPostDtoToMember(Mockito.any(MemberDto.Post.class))).willReturn(new Member());
        Member mockMember= new Member();
        mockMember.setMemberId(1L);
        given(memberService.createMember(Mockito.any(Member.class))).willReturn(mockMember);

        String content = gson.toJson(post);

        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/members/signup")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        ConstraintDescriptions postMemberConstraints = new ConstraintDescriptions(MemberDto.Post.class); // 유효성 검증 조건 정보 객체 생성
        List<String> emailDescriptions = postMemberConstraints.descriptionsForProperty("email");
        List<String> passwordDescriptions = postMemberConstraints.descriptionsForProperty("password");
        List<String> nicknameDescriptions = postMemberConstraints.descriptionsForProperty("nickname");
        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(startsWith("/members/find"))))
                .andDo(document(
                        "post-member",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                List.of(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("회원아이디")
                                                .attributes(key("constraints").value(emailDescriptions)),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                                                .attributes(key("constraints").value(passwordDescriptions)),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임")
                                                .attributes(key("constraints").value(nicknameDescriptions))
                                )
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location header. 등록된 리소스의 URI")
                        )
                ));
    }

    //통과되면 안되지...안통과되게고치기..?

    @Test
    public void patchMemberTest() throws Exception {

        Long memberId = 1L;

        MemberDto.Patch patch = new MemberDto.Patch(memberId, "dsksudgktpdy1!", "뎡이", 19, "여성", "서울시구로구온수동", Member.MemberStatus.MEMBER_JOIN);

        String content = gson.toJson(patch);

        MemberDto.Response responseDto = new MemberDto.Response(memberId,
                "aaaa1111@naver.com", "뎡이",
                19, "여성", "서울시구로구온수동",
                "Adsfdsafsa", Member.MemberStatus.MEMBER_JOIN,
                LocalDateTime.now(), LocalDateTime.now());

        given(mapper.memberPatchDtoToMember(Mockito.any(MemberDto.Patch.class))).willReturn(new Member());

        given(memberService.updateMember(Mockito.any(Member.class))).willReturn(new Member());

        given(mapper.memberToMemberResponseDto(Mockito.any(Member.class))).willReturn(responseDto);

        ResultActions actions =
                mockMvc.perform(
                        patch("/members/update/{member-id}", memberId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        ConstraintDescriptions patchMemberConstraints = new ConstraintDescriptions(MemberDto.Patch.class); // 유효성 검증 조건 정보 객체 생성
        List<String> passwordDescriptions = patchMemberConstraints.descriptionsForProperty("password");
        List<String> nicknameDescriptions = patchMemberConstraints.descriptionsForProperty("nickname");// name 필드의 유효성 검증 정보 얻기
        List<String> ageDescriptions = patchMemberConstraints.descriptionsForProperty("age");

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberId").value(memberId))
                .andExpect(jsonPath("$.nickname").value(patch.getNickname()))
                .andExpect(jsonPath("$.age").value(patch.getAge()))
                .andExpect(jsonPath("$.gender").value(patch.getGender()))
                .andExpect(jsonPath("$.address").value(patch.getAddress()))
                .andDo(document(
                        "patch-member",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("member-id").description("회원 식별자")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원식별자").ignored(),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                                                .attributes(key("constraints").value(passwordDescriptions)).optional(),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임")
                                                .attributes(key("constraints").value(nicknameDescriptions)).optional(),
                                        fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이")
                                                .attributes(key("constraints").value(ageDescriptions)).optional(),
                                        fieldWithPath("gender").type(JsonFieldType.STRING).description("성별").optional(),
                                        fieldWithPath("address").type(JsonFieldType.STRING).description("주소").optional(),
                                        fieldWithPath("memberStatus").type(JsonFieldType.STRING).description("회원 상태: MEMBER_JOIN / MEMBER_QUIT").optional()
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath(".memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath(".email").type(JsonFieldType.STRING).description("회원아이디"),
                                        fieldWithPath(".nickname").type(JsonFieldType.STRING).description("넥네임"),
                                        fieldWithPath(".age").type(JsonFieldType.NUMBER).description("나이"),
                                        fieldWithPath(".gender").type(JsonFieldType.STRING).description("성별"),
                                        fieldWithPath(".address").type(JsonFieldType.STRING).description("주소"),
                                        fieldWithPath(".memberStatus").type(JsonFieldType.STRING).description("회원상태: 가입회원/탈퇴회원"),
                                        fieldWithPath("profileImageURL").type(JsonFieldType.STRING).description("프로필 이미지 URL"),
                                        fieldWithPath(".createdAt").type(JsonFieldType.STRING).description("생성일"),
                                        fieldWithPath(".modifiedAt").type(JsonFieldType.STRING).description("수정일")

                                )
                        )));
    }

    @Test
    public void getMemberTest() throws Exception {
        Long memberId = 1L;
        //given
        MemberDto.Response response = new MemberDto.Response(1L,
                "dmswjd4015@naver.com", "서은정", 20, "여성",
                "서울시구로구", "djkafds", Member.MemberStatus.MEMBER_JOIN, LocalDateTime.now(),LocalDateTime.now());

        given(memberService.findMember(Mockito.anyLong())).willReturn(new Member());
        given(mapper.memberToMemberResponseDto(Mockito.any(Member.class))).willReturn(response);

        //when

        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/members/find/{member-id}", memberId)
                                .accept(MediaType.APPLICATION_JSON));
        //then

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberId").value(memberId))
                .andExpect(jsonPath("$.nickname").value(response.getNickname()))
                .andDo(
                        document("get-member",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        List.of(parameterWithName("member-id").description("회원식별자"))
                                ),
                                responseFields(
                                        List.of(
                                                fieldWithPath(".memberId").type(JsonFieldType.NUMBER).description("회원식별자"),
                                                fieldWithPath(".email").type(JsonFieldType.STRING).description("회원아이디"),
                                                fieldWithPath(".nickname").type(JsonFieldType.STRING).description("회원닉네임"),
                                                fieldWithPath(".age").type(JsonFieldType.NUMBER).description("나이"),
                                                fieldWithPath(".gender").type(JsonFieldType.STRING).description("성별"),
                                                fieldWithPath(".address").type(JsonFieldType.STRING).description("주소"),
                                                fieldWithPath(".memberStatus").type(JsonFieldType.STRING).description("회원 상태: MEMBER_JOIN(회원가입) /MEMBER_QUIT(회원탈퇴)"),
                                                fieldWithPath("profileImageURL").type(JsonFieldType.STRING).description("프로필 이미지 URL"),
                                                fieldWithPath(".createdAt").type(JsonFieldType.STRING).description("생성일"),
                                                fieldWithPath(".modifiedAt").type(JsonFieldType.STRING).description("수정일")
                                        )
                                )
                        )
                );

    }


    @Test
    public void getMembersTest() throws Exception {
        // given
        String page = "1";
        String size = "10";

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", page);
        queryParams.add("size", size);

        Member member1 = new Member();
        member1.setEmail("dmswjd4015@naver.com");
        member1.setPassword("dkssudgktpdy1!");
        member1.setNickname("서은정");

        Member member2 = new Member();
        member2.setNickname("zxcv123@gmail.com");
        member2.setPassword("skdiskdi1!");
        member2.setNickname("이은지");

        Page<Member> members = new PageImpl<>(List.of(member1, member2),
                PageRequest.of(0, 10, Sort.by("memberId").descending()), 2);
        List<MemberDto.Response> responses = new ArrayList<MemberDto.Response>();
        responses.add(new MemberDto.Response(1L,
                "dmswjd4015@naver.com", "서은정", 20, "여성",
                "서울시구로구", "djkafds", Member.MemberStatus.MEMBER_JOIN, LocalDateTime.now(),LocalDateTime.now()));
        responses.add(new MemberDto.Response(1L,
                "zxcv123@gmail.com", "이은지", 20, "여성",
                "서울시구로구", "djkafds", Member.MemberStatus.MEMBER_JOIN, LocalDateTime.now(),LocalDateTime.now()));


        // Stubbing
        given(memberService.findMembers(Mockito.anyInt(), Mockito.anyInt())).willReturn(members);
        given(mapper.membersToMemberResponseDtos(Mockito.anyList())).willReturn(responses);

        // when
        ResultActions actions = mockMvc.perform(
                get("/members/find")
                        .params(queryParams)
                        .accept(MediaType.APPLICATION_JSON));

        // then
        MvcResult result =
                actions
                        .andExpect(status().isOk())
                        .andDo(
                                document(
                                        "get-members",
                                        preprocessRequest(prettyPrint()),
                                        preprocessResponse(prettyPrint()),
                                        requestParameters(
                                                List.of(
                                                        parameterWithName("page").description("Page 번호"),
                                                        parameterWithName("size").description("Page Size")
                                                )
                                        ),
                                        responseFields(
                                                List.of(
                                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터").optional(),
                                                        fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                                        fieldWithPath("data[].email").type(JsonFieldType.STRING).description("회원아이디"),
                                                        fieldWithPath("data[].nickname").type(JsonFieldType.STRING).description("회원닉네임"),
                                                        fieldWithPath("data[].age").type(JsonFieldType.NUMBER).description("나이"),
                                                        fieldWithPath("data[].gender").type(JsonFieldType.STRING).description("성별"),
                                                        fieldWithPath("data[].address").type(JsonFieldType.STRING).description("주소"),
                                                        fieldWithPath("data[].memberStatus").type(JsonFieldType.STRING)
                                                                .description("회원 상태: MEMBER_JOIN(회원가입) /MEMBER_QUIT(회원탈퇴)"),
                                                        fieldWithPath("data[]profileImageURL").type(JsonFieldType.STRING).description("프로필 이미지 URL"),
                                                        fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("생성일"),
                                                        fieldWithPath("data[].modifiedAt").type(JsonFieldType.STRING).description("수정일"),
                                                        fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보"),
                                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 사이즈"),
                                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 건 수"),
                                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수")
                                                )
                                        )
                                )
                        )
                        .andReturn();

        List list = JsonPath.parse(result.getResponse().getContentAsString()).read("$.data");
        assertThat(list.size(), is(2));
    }
    @Test
    public void userDeleteMemberTest() throws Exception {
        // given
        long memberId = 1L;
        doNothing().when(memberService).userDeleteMember(Mockito.anyLong());
        // when
        ResultActions actions = mockMvc.perform(
                delete("/members/user/delete/{member-id}", memberId));
        // then
        actions.andExpect(status().isOk())
                .andDo(
                        document(
                                "user-delete-member",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        Arrays.asList(parameterWithName("member-id").description("회원 식별자 ID"))
                                )
                        )
                );
    }

    @Test
    public void adminDeleteMemberTest() throws Exception {
        // given
        long memberId = 1L;
        doNothing().when(memberService).adminDeleteMember(Mockito.anyLong());
        // when
        ResultActions actions = mockMvc.perform(
                delete("/members/admin/delete/{member-id}", memberId));
        // then
        actions.andExpect(status().isNoContent())
                .andDo(
                        document(
                                "admin-delete-member",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        Arrays.asList(parameterWithName("member-id").description("회원 식별자 ID"))
                                )
                        )
                );
    }
    @Test
    public void adminDeleteMembersTest() throws Exception {
        // given
        doNothing().when(memberService).adminDeleteMember(Mockito.anyLong());
        // when
        ResultActions actions = mockMvc.perform(
                delete("/members/admin/delete"));
        // then
        actions.andExpect(status().isNoContent())
                .andDo(
                        document(
                                "admin-delete-members",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );


    }
}