package com.malgn.configure.security;

import com.malgn.entity.Content;
import com.malgn.entity.Member;
import com.malgn.repository.ContentRepository;
import com.malgn.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("contentSecurity")
@RequiredArgsConstructor
public class ContentSecurity {
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private MemberRepository memberRepository;

    public boolean isOwner(Long contentId, String loginName){
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지않는 게시글"));

        Member member = memberRepository.findByName(content.getCreatedBy());

        return member.getName().equals(loginName);
    }
}
