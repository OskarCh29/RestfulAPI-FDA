package pl.fdaApi.restfulApi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.fdaApi.restfulApi.exception.BadRequestException;
import pl.fdaApi.restfulApi.exception.ExternalException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FdaApiService {
    private final WebClient webClient;

    public Mono<String> searchDrug(String manufacturer, String name, int limit, int page) {
        if (manufacturer.trim().isEmpty()) {
            throw new BadRequestException("Manufacturer field is required");
        }
        String query = "openfda.manufacturer_name:\"" + manufacturer.trim() + "\"";
        if (name != null && !name.trim().isEmpty()) {
            query += "+AND+" + "products.brand_name:\"" + name.trim() + "\"";
        }
        if (limit < 1 || page < 1) {
            throw new BadRequestException("limit or page parameter must be 1 or greater");
        }
        String finalQuery = query;
        int skipPage = (page - 1) * limit;
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("search", finalQuery)
                        .queryParam("limit", limit)
                        .queryParam("skip", skipPage)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        Mono.error(new ExternalException("No matches found. Check your request")))
                .bodyToMono(String.class);
    }
}
