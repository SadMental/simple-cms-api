package com.malgn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
public class ContentListDto {
    Long id;
    String title;
    Long viewCount;
    String createdBy;
    LocalDateTime createdDate;
}
