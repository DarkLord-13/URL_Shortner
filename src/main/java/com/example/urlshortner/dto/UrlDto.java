package com.example.urlshortner.dto;

import lombok.Data;


@Data
public class UrlDto{
    private Long id;
    private String url;
    private String shortCode;
    private String urlCreatedAt;
    private String urlUpdatedAt;

    public UrlDto() {}

    public UrlDto(String url){
        this.url = url;
    }
}
