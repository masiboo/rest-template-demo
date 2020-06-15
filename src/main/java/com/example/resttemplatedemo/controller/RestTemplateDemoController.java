package com.example.resttemplatedemo.controller;

import com.example.resttemplatedemo.model.ScoreBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@RestController
public class RestTemplateDemoController {

    public static final String url = "https://scoreboard-backend-dev.herokuapp.com/scoreBoard";
    public static final String POST_REQUEST = "/add/score";
    public static final String GET_ALL_REQUEST = "/all/score";
    public static final String GET_BY_ID_REQUEST = "/score/";
    public static final String PUT_REQUEST = "/update/score";
    public static final String DELETE_REQUEST = "/delete/score/";

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @GetMapping(path = GET_BY_ID_REQUEST + "{id}")
    public ScoreBoard getById(@PathVariable(required = true) long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<ScoreBoard> response = restTemplate.exchange(url + GET_BY_ID_REQUEST+id,
                HttpMethod.GET, entity, ScoreBoard.class);

        return response.getBody();
    }

    @GetMapping(GET_ALL_REQUEST)
    public String getScore() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> response = restTemplate.exchange(url + GET_ALL_REQUEST,
                HttpMethod.GET, entity, String.class);

        return response.getBody();
    }

    @PostMapping(POST_REQUEST)
    public ResponseEntity<ScoreBoard> addScore(@RequestBody ScoreBoard scoreBoard) {
        HttpEntity<?> httpEntity = new HttpEntity<>(scoreBoard);
        ResponseEntity<ScoreBoard> responseEntity = restTemplate.exchange(url + POST_REQUEST,
                HttpMethod.POST, httpEntity, ScoreBoard.class);
        return responseEntity;

    }

    @PutMapping(PUT_REQUEST)
    public ResponseEntity<ScoreBoard> updateScore(@RequestBody ScoreBoard scoreBoard) {
        // restTemplate.put(url + PUT_REQUEST, scoreBoard);
        HttpEntity<ScoreBoard> httpEntity = new HttpEntity<>(scoreBoard);
        ResponseEntity<ScoreBoard> responseEntity = restTemplate.exchange(url + PUT_REQUEST,
                HttpMethod.PUT, httpEntity, ScoreBoard.class);
        return responseEntity;

    }

    @DeleteMapping(DELETE_REQUEST + "/{id}")
    public ResponseEntity<?> deleteScore(@PathVariable(required = true) long id) {
        ResponseEntity<?> responseEntity = new ResponseEntity("Score deleted successfully", HttpStatus.OK);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<ScoreBoard> entity = new HttpEntity<>(headers);
        try {
            responseEntity = restTemplate.exchange(url + DELETE_REQUEST + id,
                    HttpMethod.DELETE, entity, String.class);
        } catch (RestClientException ex) {
            return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

}
