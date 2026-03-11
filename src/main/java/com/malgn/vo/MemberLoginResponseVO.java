package com.malgn.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "로그인 반환 데이터")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MemberLoginResponseVO {
    String loginName;
    String loginRole;
    String accessToken;
}
