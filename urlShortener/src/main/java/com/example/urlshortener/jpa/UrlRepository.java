package com.example.urlshortener.jpa;

import com.example.urlshortener.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UrlRepository extends JpaRepository<UrlEntity,Long> {
    @Query("SELECT MAX(Id) FROM UrlEntity")
    Long getMaxId();
    UrlEntity findByOriginUrl(String originUrl);
    UrlEntity findByShortUrl(String shortUrl);
}
