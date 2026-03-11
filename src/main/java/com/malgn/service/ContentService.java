package com.malgn.service;

import com.malgn.dto.ContentDto;
import com.malgn.entity.Content;
import com.malgn.error.TargetNotFoundException;
import com.malgn.repository.ContentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Slf4j
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    @Transactional
    public Content update(Long id, ContentDto contentDto){
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new TargetNotFoundException("해당 게시글이 존재하지 않습니다. ID: " + id));

        if (contentDto.getTitle() != null) {
            content.setTitle(contentDto.getTitle());
        }

        if (contentDto.getDescription() != null) {
            content.setDescription(contentDto.getDescription());
        }

        log.debug("수정된 Content = {}", content);

        return content;

    }

    @Transactional
    public Content viewDetail(Long id){
        if(contentRepository.plusViewCount(id) == 0){
            throw new TargetNotFoundException("해당 게시글이 존재하지 않습니다. ID: " + id);
        }

        return contentRepository.findById(id).get();

    }
}
