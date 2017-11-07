package com.example.consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureStubRunner(workOffline = true, ids = "com.example:Contract-Provider:+:stubs:8090")
@DirtiesContext
public class BeerConsumerTest {
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${stubrunner.runningstubs.Contract-Provider.port}")
    int producerPort;

    @Test
    public void beerCheckTest() {
        ResponseEntity<Response> response = this.restTemplate.exchange(
            RequestEntity
                .post(URI.create("http://localhost:" + producerPort + "/check"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(new Person("GilDong", 22)),
            Response.class);
        assertThat(response.getBody().status).isEqualTo(ResponseStatus.OK);
    }
}
