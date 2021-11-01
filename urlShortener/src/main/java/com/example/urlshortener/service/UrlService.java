package com.example.urlshortener.service;

import com.example.urlshortener.entity.UrlEntity;

public interface UrlService {
    UrlEntity isShorten(String originUrl);
    UrlEntity redirect(String shortUrl);
}
