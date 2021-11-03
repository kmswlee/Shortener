package com.example.urlshortener.service;

import com.example.urlshortener.entity.UrlEntity;


public interface UrlService {
    UrlEntity isShorten(String originUrl) throws Exception;
    UrlEntity redirect(String shortUrl);
}
