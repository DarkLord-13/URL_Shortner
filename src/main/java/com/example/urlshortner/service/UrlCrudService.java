package com.example.urlshortner.service;

import com.example.urlshortner.entity.Url;
import com.example.urlshortner.mapper.UrlMapper;
import com.example.urlshortner.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class UrlCrudService{

    @Autowired
    private UrlRepository repo;

    @Autowired
    private UrlMapper urlMapper;

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;
    private static final Random random = new Random();

    public Url shortenUrl(String longUrl){
        Optional<Url> existing = repo.findByUrl(longUrl);
        if(existing.isPresent()){
            throw new IllegalArgumentException("url's shortened version already exists");
        }

        String shortCode = generateShortCode();

        Url url = new Url(longUrl, shortCode);

        repo.save(url);

        return url;
    }

    public Url getOriginalUrl(String shortCode) throws Exception{
        Optional<Url> existing = repo.findByShortCode(shortCode);
        if(existing.isEmpty()){
            throw new IllegalArgumentException("no url exists for this short code");
        }

        return urlMapper.optionalToUrl(existing);
    }

    public Url updateShortCode(String longUrl, String shortCode) throws Exception{
        Optional<Url> existing = repo.findByShortCode(shortCode);

        if(existing.isEmpty()){
            throw new Exception("shortcode must exist already in order to be updated");
        }

        Url url = urlMapper.optionalToUrl(existing);
        url.setUrl(longUrl);

        repo.save(url);

        return url;
    }

    public void deleteUrl(String shortCode) throws Exception{
        Optional<Url> existing = repo.findByShortCode(shortCode);

        if(existing.isEmpty()){
            throw new Exception("no url exists mapped to the shortcode: " + shortCode);
        }

        repo.deleteById(existing.get().getId());
    }

    public static String generateShortCode(){
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<CODE_LENGTH; i++){
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }
}
