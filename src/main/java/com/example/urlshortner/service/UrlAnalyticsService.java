package com.example.urlshortner.service;

import com.example.urlshortner.entity.Url;
import com.example.urlshortner.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlAnalyticsService{

    @Autowired
    UrlRepository repo;

    public Url getGetCount(String shortCode) throws Exception{
        Optional<Url> existing = repo.findByShortCode(shortCode);

        if(existing.isEmpty()){
            throw new Exception("shortcode: " + shortCode + " does not exists");
        }

        return existing.get();
    }
}
