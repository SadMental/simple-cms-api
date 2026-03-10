package com.malgn.restcontroller;

import com.malgn.dto.ContentDto;
import com.malgn.dto.ContentListDto;
import com.malgn.entity.Content;
import com.malgn.repository.ContentRepository;
import com.malgn.repository.MemberRepository;
import com.malgn.service.ContentService;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content") @Slf4j
public class ContentRestController {

    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ContentService contentService;

    // 콘텐츠 추가
    @PostMapping("/add")
    public Content add(@RequestBody ContentDto contentDto){
        Content content = contentRepository.save(contentDto.toSave());
        log.debug("Content = {}", content);
        return content;
    }

    // 콘텐츠 목록 조회 (페이징)
    @GetMapping("/list")
    public Page<ContentListDto> list(
            @ParameterObject @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ){
        Page<ContentListDto> result = contentRepository.findAllForPage(pageable);
        log.debug("element = {}", result.getTotalElements());
        log.debug("page = {}", result.getTotalPages());

        for(ContentListDto content_list : result.getContent()) {
            log.debug(content_list.toString());
        }
        return result;
    }

    // 콘텐츠 상세 조회
    @GetMapping("/detail/{id}")
    public Content detail(@PathVariable Long id){
        Content content = contentRepository.findById(id).orElseThrow();
        log.debug("content = {}", content);
        return content;
    }

    // 콘텐츠 수정
    @PatchMapping("/edit/{id}")
    public Content edit(@PathVariable Long id,@RequestBody ContentDto contentDto){
        return contentService.update(id, contentDto);
    }

    // 콘텐츠 삭제
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id){
        contentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. ID: " + id));

        contentRepository.deleteById(id);

        log.debug("삭제된 Content ID = {}", id);

        return true;
    }

}
