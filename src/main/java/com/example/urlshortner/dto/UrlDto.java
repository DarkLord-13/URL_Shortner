package com.example.urlshortner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UrlDto{
    private Long id;
    private String url;
    private String shortCode;
    private String createdAt;
    private String updatedAt;
}
