package com.example.urlshortner.service;

import com.example.urlshortner.dto.QrDto;
import com.example.urlshortner.dto.UrlDto;
import com.example.urlshortner.entity.Url;
import com.example.urlshortner.mapper.UrlQrMapper;
import com.example.urlshortner.repository.UrlRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
public class UrlQrService{
    private static final int width = 300, height = 300;

    @Autowired
    private UrlRepository repo;

    @Autowired
    private UrlQrMapper qrMapper;

    @Autowired
    private UrlCrudService urlCrudService;

    public QrDto createQrCode(String longUrl) throws Exception{
        Optional<Url> existing = repo.findByUrl(longUrl);
        if(existing.isPresent()){
            if(existing.get().getUrlQRImage() != null){
                throw new IllegalArgumentException("qr code already exists for this url");
            }
            else{
                Url url = existing.get();
                HelperService.increaseUrlVisitCount(url);
                url.setUrlQRImage(generateQr(longUrl));

                repo.save(url);

                return qrMapper.urlToQr(url);
            }
        }
        else{
            UrlDto dto = urlCrudService.shortenUrl(longUrl); // create new record for longUrl
            existing = repo.findById(dto.getId()); // get the recently saved url
            if(existing.isEmpty()){
                throw new Exception("new url was not saved");
            }

            Url url = existing.get();
            url.setUrlQRImage(generateQr(longUrl));

            repo.save(url);

            return qrMapper.urlToQr(url);
        }
    }

    public QrDto retrieveQrCode(String longUrl) throws Exception{
        Optional<Url> existing = repo.findByUrl(longUrl);
        if(existing.isEmpty()){
            throw new Exception("url:  " + " does not exists");
        }

        Url url = existing.get();

        HelperService.increaseUrlVisitCount(url);
        repo.save(url);

        return qrMapper.urlToQr(url);
    }

    public QrDto updateQrCode(String longUrl) throws Exception{
        Optional<Url> existing = repo.findByUrl(longUrl);
        if(existing.isEmpty()){
            throw new Exception("url:  " + " does not exists");
        }

        Url url = existing.get();
        url.setUrlQRImage(generateQr(longUrl));
        repo.save(url);

        return qrMapper.urlToQr(url);
    }

    public static byte[] generateQr(String text) throws WriterException, IOException{
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // Convert BufferedImage to byte[]
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "PNG", baos); // or "JPG"
        return baos.toByteArray();
    }
}
