package com.malgn.dto;

import com.malgn.entity.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "콘텐츠 등록/수정 요청 데이터")
public class ContentDto {
    @Schema(description = "콘텐츠 고유 ID (등록 시에는 불필요)")
    Long id;

    @Schema(description = "콘텐츠 제목", example = "스프링 부트 시작하기", requiredMode = Schema.RequiredMode.REQUIRED)
    String title;

    @Schema(description = "콘텐츠 내용", example = "스프링 부트를 이용해 CMS를 만들어봅시다.")
    String description;

    @Schema(description = "콘텐츠 조회수 (등록 시에는 불필요)", hidden = true)
    Long viewCount;
    @Schema(description = "콘텐츠 등록 시각 (등록 시에는 불필요)", hidden = true)
    LocalDateTime createdDate;
    @Schema(description = "글쓴이 (등록 시에는 불필요)", hidden = true)
    String createdBy;
    @Schema(description = "콘텐츠 마지막 수정 시각 (등록 시에는 불필요)", hidden = true)
    LocalDateTime lastModifiedDate;
    @Schema(description = "마지막으로 수정한 회원 (등록 시에는 불필요)", hidden = true)
    String lastModifiedBy;

    public Content toSave() {
        return Content.builder()
                .title(this.title)
                .description(this.description)
//                .createdBy(this.createdBy)
                .build();
    }
}
