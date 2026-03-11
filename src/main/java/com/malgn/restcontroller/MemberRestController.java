package com.malgn.restcontroller;

import com.malgn.service.MemberService;
import com.malgn.vo.MemberLoginRequestVO;
import com.malgn.entity.Member;
import com.malgn.repository.MemberRepository;
import com.malgn.vo.MemberLoginResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "1. 인증 및 회원 API", description = "로그인 및 JWT 토큰 발급 관련 API")
@RestController
@RequestMapping("/")
public class MemberRestController {
    @Autowired
    private MemberService memberService;

    @Operation(summary = "로그인 (토큰 발급)", description = "회원 이름(name)과 비밀번호를 입력하여 로그인을 수행하고 JWT 액세스 토큰을 발급받습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공 (토큰 발급 완료)"),
            @ApiResponse(responseCode = "400", description = "비밀번호 불일치 등 잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 회원"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @PostMapping("/login")
    public MemberLoginResponseVO login(@RequestBody MemberLoginRequestVO memberLoginRequestVO){
        return memberService.login(memberLoginRequestVO);
    }

}
