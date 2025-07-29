package com.example.urlshortner.service;

import com.example.urlshortner.dto.UrlDto;
import com.example.urlshortner.entity.Url;
import com.example.urlshortner.mapper.UrlDtoMapper;
import com.example.urlshortner.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlCrudService{

    @Autowired
    private UrlRepository repo;

    @Autowired
    private UrlDtoMapper urlDtoMapper;

    public UrlDto shortenUrl(String longUrl){
        Optional<Url> existing = repo.findByUrl(longUrl);
        if(existing.isPresent()){
            throw new IllegalArgumentException("url's shortened version already exists");
        }

        String shortCode = HelperService.generateShortCode();

        Url url = new Url(longUrl, shortCode);

        repo.save(url);

        return urlDtoMapper.urlToDto(url);
    }

    public UrlDto shortenUrl(String longUrl, String shortCode){
        Optional<Url> existing = repo.findByUrl(longUrl);

        if(existing.isPresent() || repo.findByShortCode(shortCode).isPresent()){
            throw new IllegalArgumentException("url's shortened version already exists");
        }

        Url url = new Url(longUrl, shortCode);

        repo.save(url);

        return urlDtoMapper.urlToDto(url);
    }

    public UrlDto getOriginalUrl(String shortCode) throws Exception{
        Optional<Url> existing = repo.findByShortCode(shortCode);
        if(existing.isEmpty()){
            throw new IllegalArgumentException("no url exists for this short code");
        }

        Url url = existing.get();

        HelperService.increaseUrlVisitCount(url);
        repo.save(url);

        return urlDtoMapper.urlToDto(url);
    }

    public UrlDto updateShortCode(String longUrl, String shortCode) throws Exception{
        Optional<Url> existing = repo.findByShortCode(shortCode);

        if(existing.isEmpty()){
            throw new Exception("shortcode must exist already in order to be updated");
        }
        else if(repo.findByUrl(longUrl).isPresent()){
            throw new Exception("this longurl is already mapped to someone else, please select another longurl");
        }

        Url url = existing.get();
        url.setUrl(longUrl);
        url.setUrlQRImage(UrlQrService.generateQr(longUrl));
        repo.save(url);

        return urlDtoMapper.urlToDto(url);
    }

    public void deleteUrl(String shortCode) throws Exception{
        Optional<Url> existing = repo.findByShortCode(shortCode);

        if(existing.isEmpty()){
            throw new Exception("no url exists mapped to the shortcode: " + shortCode);
        }

        repo.deleteById(existing.get().getId());
    }
}
