package pl.fdaApi.restfulApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest
public class FdaApiServiceTest {

    @Autowired
    private WebClient webClient;


}
