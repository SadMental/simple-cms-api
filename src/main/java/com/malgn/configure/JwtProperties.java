package com.malgn.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "custom.jwt")
public class JwtProperties {
    private String keyStr;//custom.jwt.key-str을 불러와서 저장하세요
    private String issuer;
    private int expiration;//엑세스토큰 만료시간(분)
}
