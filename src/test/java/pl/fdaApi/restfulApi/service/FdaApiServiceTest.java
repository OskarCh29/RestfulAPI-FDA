package pl.fdaApi.restfulApi.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FdaApiServiceTest {

    private FdaApiService fdaApiService;

    private WebClient webClient;

    private WireMockServer wireMockServer;

    @BeforeEach
    public void setUp() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8081));
        wireMockServer.start();
        WireMock.configureFor("localhost", 8081);
        webClient = WebClient.builder().baseUrl(
                "http://localhost:" + wireMockServer.port()).build();
        fdaApiService = new FdaApiService(webClient);
    }

    @AfterEach
    public void tearDown() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @Test
    void searchDrug_found() {
        String manufacturer = "testManufacturer";
        String name = "testDrug";
        int limit = 5;
        int page = 5;

        String finalQuery = "openfda.manufacturer_name:\"testManufacturer\"+AND+products.brand_name:\"testDrug\"";
        int skipPage = (page - 1) * limit;
        System.out.println("--------------------------------------");
        System.out.println(finalQuery);
        System.out.println("--------------------------------------");
        stubFor(get(urlPathEqualTo("/"))
                .withQueryParam("search", equalTo(finalQuery))
                .withQueryParam("limit", equalTo(String.valueOf(limit)))
                .withQueryParam("skip", equalTo(String.valueOf(skipPage)))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("{\"result\":\"success\"}")));
        Mono<String> resultMono = fdaApiService.searchDrug(manufacturer, name, limit, page);
        String result = resultMono.block();
        assertEquals("{\"result\":\"success\"}", result);
    }
}
