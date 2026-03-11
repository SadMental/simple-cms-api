package com.malgn.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "콘텐츠 목록 반환 데이터")
@Data @AllArgsConstructor
public class ContentListDto {
    Long id;
    String title;
    Long viewCount;
    String createdBy;
    LocalDateTime createdDate;
}
