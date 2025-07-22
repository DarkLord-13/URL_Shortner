package com.example.urlshortner.mapper;

import com.example.urlshortner.entity.Url;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Data
@Component
public class UrlOptionalMapper {
    public Url optionalToUrl(Optional<Url> optional) throws Exception{
        if(optional.isEmpty()){
            throw new Exception("expected Optional<Url>, but its empty");
        }

        Url url = new Url();

        url.setId(optional.get().getId());
        url.setUrl(optional.get().getUrl());
        url.setShortCode(optional.get().getShortCode());
        url.setUrlCreatedAt(optional.get().getUrlCreatedAt());
        url.setUrlUpdatedAt(optional.get().getUrlUpdatedAt());

        return url;
    }
}
