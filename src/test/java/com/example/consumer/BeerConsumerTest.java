package com.example.consumer;

import lombok.Builder;
import lombok.Data;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class BeerConsumerTest {
    RestTemplate restTemplate = new RestTemplate();

    int port = 8090;

    @Test
    public void beerCheckTest() {
        ResponseEntity<Response> response = this.restTemplate.exchange(
            RequestEntity
                .post(URI.create("http://localhost:" + port + "/check"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(new Person("GilDong,Hong", 22)),
            Response.class);
        assertThat(response.getBody().status).isEqualTo(ResponseStatus.OK);
    }

    @Data
    public static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    public static class Response {
        ResponseStatus status;
    }

    public enum ResponseStatus {
        OK, NOT_OK
    }
}
