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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@Controller
public class UrlController {
    private final UrlService urlService;
    UrlRepository urlRepository;

    @Autowired
    public UrlController(UrlService urlService, UrlRepository urlRepository){
        this.urlService = urlService;
        this.urlRepository = urlRepository;
    }
      /* thymeleaf 사용이 아닌 다른 프레임워크 사용시 Json 형식으로 return */
//    @CrossOrigin(origins = "*")
//    @PostMapping("/url")
//    public ResponseEntity<String> Shorten(@RequestBody RequestUrl originUrl) throws Exception {
//        ModelMapper mapper = new ModelMapper();
//        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        UrlDto urlDto = mapper.map(urlService.isShorten(originUrl.getOriginUrl()),UrlDto.class);
//        ResponseUrl responseUrl = mapper.map(urlDto,ResponseUrl.class);
//        return ResponseEntity.status(HttpStatus.CREATED).body(responseUrl.getShortUrl());
//    }

    /* thx for ref : https://velog.io/@haerong22/Thymeleaf-%EC%A0%95%EB%A6%AC */
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @PostMapping("/")
    public String Short(@RequestParam String originUrl,Model model) throws Exception{
        model.addAttribute("originUrl",originUrl);
        model.addAttribute("shortUrl",urlService.isShorten(originUrl).getShortUrl());
        return "index";
    }

    @CrossOrigin("*")
    @GetMapping("/{shortUrl}")
    public RedirectView redirect(@PathVariable String shortUrl){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UrlDto urlDto = mapper.map(urlService.redirect(shortUrl),UrlDto.class);
        ResponseUrl responseUrl = mapper.map(urlDto,ResponseUrl.class);
        /* thx for ref : http://jmlim.github.io/spring/2019/09/30/spring-redirect-to-an-external-url/ */
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(responseUrl.getOriginUrl());
        return redirectView;
    }

}
