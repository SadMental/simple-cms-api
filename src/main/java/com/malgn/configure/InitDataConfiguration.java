package com.malgn.configure;

import com.malgn.entity.Member;
import com.malgn.enums.Role;
import com.malgn.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor @Slf4j
public class InitDataConfiguration implements CommandLineRunner {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final int CREATE_MEMBER_LENGTH = 10;
    @Override
    public void run(String... args) throws Exception{
        if (memberRepository.count() == 0) {
            List<Member> memberList = new ArrayList<>();

            for(int i = 0; i < CREATE_MEMBER_LENGTH; i++){
                Member admin = Member.builder()
                        .name("admin" + i)
                        .password(passwordEncoder.encode("1234"))
                        .role(Role.ADMIN)
                        .build();

                memberRepository.save(admin);
                Member user = Member.builder()
                        .name("user" + i)
                        .password(passwordEncoder.encode("1234"))
                        .build();
                memberList.add(admin);
                memberList.add(user);
            }


            memberRepository.saveAll(memberList);
            for(Member member : memberList){
                log.debug("Create Member({} / {})", member.getName(), member.getPassword());
            }
        }

    }
}
