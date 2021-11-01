package com.example.urlshortener.service;

import com.example.urlshortener.entity.UrlEntity;
import com.example.urlshortener.jpa.UrlRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Slf4j
public class UrlServiceImpl implements UrlService{
    UrlRepository urlRepository;
    private static final Integer BASE62 = 62;

    @Value("${string.base62}")
    private String base62String;

    @Autowired
    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    private String base62Encode(int number) {
        char[] table = base62String.toCharArray();
        StringBuilder sb = new StringBuilder();
        while (number > 0){
            sb.append(table[number%BASE62]);
            number /= BASE62;
        }
        return sb.toString();
    }
    private int findMaxId(){
        if (urlRepository.getMaxId() == null) {
            return 1;
        }
        return (int)(urlRepository.getMaxId()+1);
    }

    private String validHttpHeader(String originUrl) {
        /* To Do */
        return null;
    }

    @Transactional
    public UrlEntity makeShortUrl(String originUrl){
        int hash = findMaxId();
        UrlEntity urlEntity = UrlEntity.builder()
                                .originUrl(originUrl)
                                .shortUrl("http://localhost:8085/"+base62Encode(hash))
                                .build();
        return urlRepository.save(urlEntity);
    }

    @Transactional
    @Override
    public UrlEntity isShorten(String originUrl){
        return Optional.ofNullable(urlRepository.findByOriginUrl(originUrl))
                .orElseGet(()->makeShortUrl(originUrl));
    }

    @Override
    public UrlEntity redirect(String shortUrl) {
        return Optional.ofNullable(urlRepository.findByShortUrl(shortUrl))
                .orElseThrow(()->new EntityNotFoundException("등록되지 않은 URL 입니다."));
    }

}
