package com.malgn.vo;

import com.malgn.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "로그인 요청 데이터")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MemberLoginRequestVO {

    @Schema(description = "로그인 아이디", example = "admin0-9 | user0-9")
    String name;

    @Schema(description = "비밀번호", example = "1234")
    String password;
}
