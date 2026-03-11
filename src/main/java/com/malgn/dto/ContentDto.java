package com.malgn.dto;

import com.malgn.entity.Content;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContentDto {
    Long id;
    String title;
    String description;
    Long viewCount;
    LocalDateTime createdDate;
    String createdBy;
    LocalDateTime lastModifiedDate;
    String lastModifiedBy;

    public Content toSave() {
        return Content.builder()
                .title(this.title)
                .description(this.description)
//                .createdBy(this.createdBy)
                .build();
    }
}
