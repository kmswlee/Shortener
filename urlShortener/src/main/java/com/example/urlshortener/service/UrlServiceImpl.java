package com.example.urlshortener.service;

import com.example.urlshortener.entity.UrlEntity;
import com.example.urlshortener.jpa.UrlRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
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
    /* base62 인코딩 */
    /* thx for ref : https://pjh3749.tistory.com/232?category=766108 */
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
    /* Q : Impl에서 하는게 맞는지, Controller에서 하는게 맞는지. */
    private static boolean isUrl(String url) {
        return new UrlValidator().isValid(url);
    }

    @Transactional
    public UrlEntity makeShortUrl(String originUrl){
        /* unique한 db id로 해쉬코드 생성 */
        int hash = findMaxId();
        UrlEntity urlEntity = UrlEntity.builder()
                                .originUrl(originUrl)
                                .shortUrl("http://localhost:8085/" + base62Encode(hash))
                                .build();
        return urlRepository.save(urlEntity);
    }

    @Transactional
    @Override
    public UrlEntity isShorten(String originUrl) throws Exception {
        if(isUrl(originUrl)) { /* 올바른 URL 형식인지 체크 */
            /* origin URL이 존재하는지 체크, 존재한다면 기존 shorten URL 리턴, 없다면 생성후 리턴 */
            return Optional.ofNullable(urlRepository.findByOriginUrl(originUrl))
                    .orElseGet(()->makeShortUrl(originUrl));
        }
        throw new Exception("올바르지 않는 URL 형식입니다. 올바르게 입력해주세요.");
    }

    @Override
    public UrlEntity redirect(String shortUrl) {
        return Optional.ofNullable(urlRepository.findByShortUrl("http://localhost:8085/"+shortUrl))
                .orElseThrow(()->new EntityNotFoundException("등록되지 않은 URL 입니다."));
    }

}
