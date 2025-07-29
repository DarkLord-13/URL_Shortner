package com.example.urlshortner.dto;

import lombok.Data;

@Data
public class QrDto{
    private Long id;
    private String url;
    private String shortCode;
    private byte[] urlQRImage;

    public QrDto() {}
}
