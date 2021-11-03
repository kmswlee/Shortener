package com.example.urlshortener.vo;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class RequestUrl {
    /* 빈칸입력 체크 */
    @NotBlank(message = "url를 입력해주세요")
    private String originUrl;
}
