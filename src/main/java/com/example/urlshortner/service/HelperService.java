package com.example.urlshortner.service;

import com.example.urlshortner.entity.Url;

import java.util.Random;

public class HelperService{
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;
    private static final Random random = new Random();

    public static String generateShortCode(){
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<CODE_LENGTH; i++){
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }

    public static void increaseUrlVisitCount(Url url){
        long prevGetCount = url.getGetCount();
        url.setGetCount(prevGetCount + 1);
        System.out.println(url);
    }
}
