package com.example.urlshortener.controller;

import com.example.urlshortener.dto.UrlDto;
import com.example.urlshortener.jpa.UrlRepository;
import com.example.urlshortener.service.UrlService;
import com.example.urlshortener.vo.RequestUrl;
import com.example.urlshortener.vo.ResponseUrl;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
@Slf4j
public class UrlController {
    private final UrlService urlService;
    UrlRepository urlRepository;

    @Autowired
    public UrlController(UrlService urlService, UrlRepository urlRepository){
        this.urlService = urlService;
        this.urlRepository = urlRepository;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/url")
    public ResponseEntity<String> Shorten(@RequestBody RequestUrl originUrl) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UrlDto urlDto = mapper.map(urlService.isShorten(originUrl.getOriginUrl()),UrlDto.class);
        ResponseUrl responseUrl = mapper.map(urlDto,ResponseUrl.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUrl.getShortUrl());
    }

    @CrossOrigin("*")
    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> redirect(@PathVariable String shortUrl){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UrlDto urlDto = mapper.map(urlService.redirect(shortUrl),UrlDto.class);
        ResponseUrl responseUrl = mapper.map(urlDto,ResponseUrl.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUrl.getOriginUrl());
    }

}
