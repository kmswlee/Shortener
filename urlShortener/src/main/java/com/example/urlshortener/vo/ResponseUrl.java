package com.example.urlshortener.vo;

import lombok.Data;

@Data
public class ResponseUrl {
    private String originUrl;
    private String shortUrl;
}