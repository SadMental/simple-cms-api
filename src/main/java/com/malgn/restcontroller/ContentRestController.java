package com.malgn.restcontroller;

import com.malgn.dto.ContentDto;
import com.malgn.dto.ContentListDto;
import com.malgn.entity.Content;
import com.malgn.error.TargetNotFoundException;
import com.malgn.repository.ContentRepository;
import com.malgn.repository.MemberRepository;
import com.malgn.service.ContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "2. 콘텐츠 API", description = "콘텐츠(콘텐츠) 등록, 수정, 삭제, 조회 관련 API")
@RestController
@RequestMapping("/content") @Slf4j
public class ContentRestController {

    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private ContentService contentService;

    // 콘텐츠 추가
    @Operation(summary = "콘텐츠 등록", description = "새로운 콘텐츠를 등록합니다. (USER, ADMIN 권한 필요)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "등록 성공"),
            @ApiResponse(responseCode = "401", description = "로그인 필요 (토큰 없음)"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Content add(@RequestBody ContentDto contentDto){

        Content content = contentRepository.save(contentDto.toSave());
        log.debug("Content = {}", content);
        return content;
    }

    // 콘텐츠 목록 조회 (페이징)
    @Operation(summary = "콘텐츠 목록 조회", description = "페이징 처리된 콘텐츠 목록을 가져옵니다.")
    @Parameters({
            @Parameter(name = "page", description = "조회할 페이지 번호 **(0부터 시작)**", example = "0"),
            @Parameter(name = "size", description = "한 페이지에 보여줄 데이터 개수 (기본값: 10)", example = "10"),
            @Parameter(name = "sort", description = "정렬 기준 (컬럼명,asc|desc) (기본값: id,desc)", example = "id,desc")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/list")
    public Page<ContentListDto> list(
            @Parameter(hidden = true) @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
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
    @Operation(summary = "콘텐츠 상세 조회", description = "콘텐츠 ID를 이용해 특정 콘텐츠의 상세 정보를 조회합니다. (작성한 본인이 아닐시 조회수 1 증가)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글")
    })
    @GetMapping("/detail/{id}")
    public Content detail(
            @Parameter(description = "조회할 콘텐츠의 고유 ID", example = "1")
            @PathVariable Long id){
        Content content = contentService.viewDetail(id);
        log.debug("content = {}", content);
        return content;
    }

    // 콘텐츠 수정
    @Operation(summary = "콘텐츠 수정", description = "작성자 본인 또는 관리자만 해당 콘텐츠를 수정할 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "401", description = "로그인 필요"),
            @ApiResponse(responseCode = "403", description = "수정 권한 없음 (본인 글 아님)"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글")
    })
    @PatchMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN') or @contentSecurity.isOwner(#id, authentication.name)")
    public Content edit(
            @Parameter(description = "수정할 콘텐츠의 고유 ID", example = "1")
            @PathVariable Long id,
            @RequestBody ContentDto contentDto){
        return contentService.update(id, contentDto);
    }

    // 콘텐츠 삭제
    @Operation(summary = "콘텐츠 삭제", description = "작성자 본인 또는 관리자만 삭제할 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "로그인 필요"),
            @ApiResponse(responseCode = "403", description = "삭제 권한 없음 (본인 글 아님)"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글")
    })
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN') or @contentSecurity.isOwner(#id, authentication.name)")
    public boolean delete(
            @Parameter(description = "삭제할 콘텐츠의 고유 ID", example = "1")
            @PathVariable Long id){
        contentRepository.deleteById(id);

        log.debug("삭제된 Content ID = {}", id);

        return true;
    }

}
