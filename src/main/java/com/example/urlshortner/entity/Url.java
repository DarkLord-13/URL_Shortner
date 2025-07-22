package com.example.urlshortner.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "url_shortner")
public class Url{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url", nullable = false, unique = true)
    private String url;

    @Column(name = "short_code", nullable = false, unique = true)
    private String shortCode;

    @Column(name = "url_created_at", nullable = false, updatable = false)
    private String urlCreatedAt =java.time.LocalDateTime.now().toString();

    @Column(name = "url_updated_at", nullable = false)
    private String urlUpdatedAt =java.time.LocalDateTime.now().toString();

    public Url(String longUrl, String shortCode){
        this.url = longUrl;
        this.shortCode = shortCode;
    }

    public Url() {}
}
