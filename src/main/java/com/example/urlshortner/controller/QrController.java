package com.example.urlshortner.controller;


import com.example.urlshortner.dto.QrDto;
import com.example.urlshortner.dto.UrlDto;
import com.example.urlshortner.service.UrlQrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/url/qr")
public class QrController{
    @Autowired
    private UrlQrService qrService;

    @PostMapping
    public ResponseEntity<QrDto> createQrCode(@RequestBody UrlDto url){
        try{
            return new ResponseEntity<>(qrService.createQrCode(url.getUrl()), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<byte[]> getQrCode(@RequestBody UrlDto url){
        try{
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(qrService.retrieveQrCode(url.getUrl()).getUrlQRImage());
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<QrDto> updateQrCode(@RequestBody UrlDto url){
        try{
            return new ResponseEntity<>(qrService.updateQrCode(url.getUrl()), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
