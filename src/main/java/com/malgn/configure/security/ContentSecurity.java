package com.malgn.configure.security;

import com.malgn.entity.Content;
import com.malgn.entity.Member;
import com.malgn.error.TargetNotFoundException;
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

    public boolean isOwner(Long contentId, String loginName){
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new TargetNotFoundException("해당 게시글이 존재하지 않습니다. ID: " + contentId));

        return content.getCreatedBy().equals(loginName);
    }
}
