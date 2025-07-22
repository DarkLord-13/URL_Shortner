package com.example.urlshortner.mapper;

import com.example.urlshortner.dto.UrlDto;
import com.example.urlshortner.entity.Url;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UrlDtoMapper{

    public UrlDto urlToDto(Url url){
        UrlDto dto = new UrlDto();

        dto.setId(url.getId());
        dto.setUrl(url.getUrl());
        dto.setShortCode(url.getShortCode());
        dto.setUrlCreatedAt(url.getUrlCreatedAt());
        dto.setUrlUpdatedAt(url.getUrlUpdatedAt());

        return dto;
    }

    public Url dtoToUrl(UrlDto dto){
        Url url = new Url();

        url.setId(dto.getId());
        url.setUrl(dto.getUrl());
        url.setShortCode(dto.getShortCode());
        url.setUrlCreatedAt(dto.getUrlCreatedAt());
        url.setUrlUpdatedAt(dto.getUrlUpdatedAt());

        return url;
    }
}
