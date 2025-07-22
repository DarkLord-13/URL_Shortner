package com.example.urlshortner.controller;

import com.example.urlshortner.dto.UrlDto;
import com.example.urlshortner.service.UrlCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shorten")
public class UrlCrudController{

    @Autowired
    UrlCrudService urlCrudService;

    @PostMapping
    public ResponseEntity<?> shortenUrl(@RequestBody UrlDto urlDto){
        try{
            return new ResponseEntity<>(urlCrudService.shortenUrl(urlDto.getUrl()), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<?> retrieveUrl(@PathVariable("shortCode") String shortCode){
        try{
            return new ResponseEntity<>(urlCrudService.getOriginalUrl(shortCode), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{shortCode}")
    public ResponseEntity<?> updateUrl(@RequestBody UrlDto urlDto, @PathVariable("shortCode") String shortCode){
        try{
            return new ResponseEntity<>(urlCrudService.updateShortCode(urlDto.getUrl(), shortCode), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{shortCode}")
    public ResponseEntity<?> deleteUrl(@PathVariable("shortCode") String shortCode){
        try{
            urlCrudService.deleteUrl(shortCode);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
        }
    }
}
