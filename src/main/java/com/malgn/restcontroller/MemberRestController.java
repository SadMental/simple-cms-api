package com.malgn.restcontroller;

import com.malgn.service.MemberService;
import com.malgn.vo.MemberLoginRequestVO;
import com.malgn.entity.Member;
import com.malgn.repository.MemberRepository;
import com.malgn.vo.MemberLoginResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class MemberRestController {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    @PostMapping("/login")
    public MemberLoginResponseVO login(@RequestBody MemberLoginRequestVO memberLoginRequestVO){
        return memberService.login(memberLoginRequestVO);


    }
}
