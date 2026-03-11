package com.malgn.service;

import com.malgn.configure.aop.JwtProperties;
import com.malgn.entity.Member;
import com.malgn.repository.MemberRepository;
import com.malgn.vo.MemberLoginRequestVO;
import com.malgn.vo.MemberLoginResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;

    public MemberLoginResponseVO login(MemberLoginRequestVO memberLoginRequestVO){
        String name = memberLoginRequestVO.getName();
        String password = memberLoginRequestVO.getPassword();

        Member member = memberRepository.findByName(name);

        if (!passwordEncoder.matches(password, member.getPassword())){
            throw new IllegalArgumentException("비밀번호 불일치");
        }

        String role = member.getRole().name();
        String accessToken = tokenService.generateAccessToken(member);

        return MemberLoginResponseVO.builder()
                .loginName(member.getName())
                .loginRole(role)
                .accessToken(accessToken)
                .build();
    }
}
