package com.developerDev.Libris.Service;

import com.developerDev.Libris.JsonResposeEntity.Kittens;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class HomeService {

    private final RestTemplate restTemplate;
    private final String url="https://catfact.ninja/fact";


    public HomeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public Kittens getAllBooks(){

        ResponseEntity<Kittens> response = restTemplate.exchange(url, HttpMethod.GET,null,Kittens.class);
        return response.getBody();


    }


}
