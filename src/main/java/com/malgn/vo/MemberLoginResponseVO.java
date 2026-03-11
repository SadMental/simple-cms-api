package com.malgn.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MemberLoginResponseVO {
    String loginName;
    String loginRole;
    String accessToken;
}
