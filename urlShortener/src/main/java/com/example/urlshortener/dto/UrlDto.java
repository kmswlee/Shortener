package com.example.urlshortener.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UrlDto {
    private String originUrl;
    private String shortUrl;

    @Builder
    public UrlDto(String originUrl,String shortUrl){
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
    }
}
