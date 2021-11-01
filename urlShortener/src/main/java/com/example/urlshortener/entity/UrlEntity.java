package com.example.urlshortener.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "urls")
public class UrlEntity {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long Id;

    @Column
    private String shortUrl;

    @Column
    private String originUrl;

    @Builder
    public UrlEntity(Long Id,String shortUrl,String originUrl){
        this.Id = Id;
        this.shortUrl = shortUrl;
        this.originUrl = originUrl;
    }
}
