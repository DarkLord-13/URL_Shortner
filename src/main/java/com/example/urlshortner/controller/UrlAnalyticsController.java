package com.example.urlshortner.controller;

import com.example.urlshortner.service.UrlAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shorten/{shortCode}/stats")
public class UrlAnalyticsController{

    @Autowired
    UrlAnalyticsService urlAnalyticsService;

    @GetMapping
    public ResponseEntity<?> getUrlAccessCount(@PathVariable("shortCode") String shortCode){
        try{
            return new ResponseEntity<>(urlAnalyticsService.getGetCount(shortCode), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
