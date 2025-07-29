package com.example.urlshortner.mapper;

import com.example.urlshortner.dto.QrDto;
import com.example.urlshortner.entity.Url;
import org.springframework.stereotype.Component;

@Component
public class UrlQrMapper{
    public QrDto urlToQr(Url url){
        QrDto qrDto = new QrDto();
        qrDto.setId(url.getId());
        qrDto.setUrl(url.getUrl());
        qrDto.setShortCode(url.getShortCode());
        qrDto.setUrlQRImage(url.getUrlQRImage());

        return qrDto;
    }
}
