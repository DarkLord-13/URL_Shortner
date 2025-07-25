package com.example.urlshortner.repository;

import com.example.urlshortner.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long>{
    Optional<Url> findByUrl(String url);

    Optional<Url> findByShortCode(String shortCode);
}
