package com.example.urlshortner.controller;

import com.example.urlshortner.entity.Url;
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
    public ResponseEntity<?> shortenUrl(@RequestBody String longUrl){
        try{
            Url url = urlCrudService.shortenUrl(longUrl);
            return new ResponseEntity<>(url, HttpStatus.OK);
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
    public ResponseEntity<?> updateUrl(@RequestBody String longUrl, @PathVariable("shortCode") String shortCode){
        try{
            return new ResponseEntity<>(urlCrudService.updateShortCode(longUrl, shortCode), HttpStatus.OK);
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
