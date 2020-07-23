package com.example.resttemplatedemo.controller;

import com.example.resttemplatedemo.model.ScoreBoard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class RestTemplateDemoControllerTest {

    @InjectMocks
    private RestTemplateDemoController restTemplateDemoController;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getScore() {
        // Arrangement
        String expected = "All score here";
        ResponseEntity<String> mocResponse = new ResponseEntity<>(expected, HttpStatus.OK);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        // Act
        when(restTemplate.exchange(RestTemplateDemoController.url
                        + RestTemplateDemoController.GET_ALL_REQUEST,
                HttpMethod.GET, entity, String.class))
                .thenReturn(mocResponse);
        String actual = restTemplateDemoController.getScore();

        // assert
        assertNotNull(actual);
        assertSame(expected, actual);

    }

    @Test
    void addScore() {
        // Arrangement
        ScoreBoard expectedScoreBoard = new ScoreBoard();
        expectedScoreBoard.setId(0);
        expectedScoreBoard.setPoint(10);
        expectedScoreBoard.setTeam("team");
        HttpEntity<?> entity = new HttpEntity<>(expectedScoreBoard);
        ResponseEntity<ScoreBoard> responseEntity = new ResponseEntity<>(expectedScoreBoard, HttpStatus.OK);

        // Act
        when(restTemplate.exchange(RestTemplateDemoController.url
                        + RestTemplateDemoController.POST_REQUEST,
                HttpMethod.POST,
                entity,
                ScoreBoard.class))
                .thenReturn(responseEntity);

        ResponseEntity<ScoreBoard> response = restTemplateDemoController
                .addScore(expectedScoreBoard);

        // assert
        assertNotNull(response);
        assertSame(entity.getBody(), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateScore() {
        // Arrangement
        ScoreBoard expectedScoreBoard = new ScoreBoard();
        expectedScoreBoard.setId(0);
        expectedScoreBoard.setPoint(10);
        expectedScoreBoard.setTeam("team");
        HttpEntity<ScoreBoard> httpEntity = new HttpEntity<>(expectedScoreBoard);
        ResponseEntity<ScoreBoard> expectedResponseEntity = new ResponseEntity<>(expectedScoreBoard, HttpStatus.OK);

        // Act
        when(restTemplate.exchange(RestTemplateDemoController.url
                        + RestTemplateDemoController.PUT_REQUEST,
                HttpMethod.PUT,
                httpEntity,
                ScoreBoard.class))
                .thenReturn(expectedResponseEntity);

        ResponseEntity<ScoreBoard> updatedEntity = restTemplateDemoController
                .updateScore(expectedScoreBoard);

        // assert
        assertEquals(HttpStatus.OK.value(), updatedEntity.getStatusCodeValue());
        assertSame(expectedScoreBoard, updatedEntity.getBody());
    }

    @Test
    void deleteScore() {

        // Arrange
        long id = 1L;
        ResponseEntity<String> expectedResponseEntity = new ResponseEntity<>("Score deleted successfully",
                HttpStatus.OK);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<ScoreBoard> entity = new HttpEntity<>(headers);

        // act
        when(restTemplate.exchange(RestTemplateDemoController.url +
                        RestTemplateDemoController.DELETE_REQUEST + id,
                HttpMethod.DELETE, entity, String.class)).thenReturn(expectedResponseEntity);

        ResponseEntity<?> actualResponseEntity = restTemplateDemoController.deleteScore(id);

        assertNotNull(actualResponseEntity);
        assertSame(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    void deleteScoreNotFound() {

        // Arrange
        long id = 0;
        ResponseEntity<String> expectedResponseEntity = new ResponseEntity<>("Score deleted successfully",
                HttpStatus.OK);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<ScoreBoard> entity = new HttpEntity<>(headers);

        // act
        ResponseEntity<?> actualResponseEntity = restTemplateDemoController.deleteScore(id);

        assertNull(actualResponseEntity);
    }

    @Test
    void getById() {
        // Arrange
        long id = 1L;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ScoreBoard expectedScoreBoard = new ScoreBoard();
        expectedScoreBoard.setId(0);
        expectedScoreBoard.setPoint(10);
        expectedScoreBoard.setTeam("team");
        ResponseEntity<ScoreBoard> expectedEntity = new ResponseEntity<>(expectedScoreBoard, HttpStatus.OK);

        // act
        when(restTemplate.exchange(restTemplateDemoController.url +
                        restTemplateDemoController.GET_BY_ID_REQUEST + id,
                HttpMethod.GET, entity, ScoreBoard.class)).thenReturn(expectedEntity);

        ScoreBoard actualScoreBoard = restTemplateDemoController.getById(id);

        // assert
        assertNotNull(actualScoreBoard);
        assertSame(expectedScoreBoard, actualScoreBoard);
    }
}