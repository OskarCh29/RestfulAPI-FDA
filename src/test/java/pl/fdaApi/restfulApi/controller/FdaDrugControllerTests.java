package pl.fdaApi.restfulApi.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FdaDrugControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    private static WireMockServer wireMockServer;

    @BeforeAll
    public static void setUpTests() {
        wireMockServer = new WireMockServer(options().dynamicPort());
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
    }

    @AfterAll
    public static void tearDownWireMock() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @DynamicPropertySource
    public static void addDynamicUrl(DynamicPropertyRegistry registry) {
        registry.add("apiAddress.baseUrl", wireMockServer::baseUrl);
    }

    @Test
    public void searchDrug_Status200() {
        String responseMock = "{ \"results\": [{ \"id\": \"123\", \"name\": \"Test Drug\" }] }";

        wireMockServer.stubFor(get(urlPathMatching("/.*"))
                .withQueryParam("limit", equalTo("1"))
                .withQueryParam("search", containing("openfda.manufacturer_name"))
                .withQueryParam("search", containing("openfda.brand_name"))
                .withQueryParam("search", containing("AND"))
                .willReturn(ok().withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(responseMock)));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/drug/search")
                        .queryParam("manufacturer", "TestManufacturer")
                        .queryParam("name", "TestDrug")
                        .queryParam("limit", 1)
                        .queryParam("page", 1)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .json(responseMock);
    }

    @Test
    public void searchDrug_ManufacturerFieldEmpty_Status400() {
        String response = "Manufacturer field is required";
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/drug/search")
                        .queryParam("manufacturer", "")
                        .queryParam("name", "TestDrug")
                        .queryParam("limit", 1)
                        .queryParam("page", 1)
                        .build())
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class).isEqualTo(response);

    }

    @Test
    public void searchDrug_limitInvalidInput_Status400() {
        String response = "limit or page parameter must be 1 or greater";
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/drug/search")
                        .queryParam("manufacturer", "Manufacturer")
                        .queryParam("name", "TestDrug")
                        .queryParam("limit", -11)
                        .queryParam("page", 1)
                        .build())
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class).isEqualTo(response);

    }

    @Test
    public void searchDrug_ExternalError() {
        wireMockServer.stubFor(get(urlEqualTo("/drug/search"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .withBody("{\"error\": \"No matches found. Check your request\"}")
                        .withHeader("Content-Type", "application/json")));


        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/drug/search")
                        .queryParam("manufacturer", "TestManufacturer")
                        .queryParam("name", "TestDrug")
                        .queryParam("limit", 1)
                        .queryParam("page", 1)
                        .build())
                .exchange()
                .expectBody(String.class).isEqualTo("No matches found. Check your request");

    }
}
