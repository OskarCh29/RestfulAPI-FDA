package pl.fdaApi.restfulApi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.fdaApi.restfulApi.exception.BadRequestException;
import pl.fdaApi.restfulApi.repository.DrugRecordRepository;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FdaApiService {
    private final WebClient webClient;
    private final DrugRecordRepository drugRecordRepository;

    public Mono<String> searchDrug(String manufacturer, String name, int limit, int page) {
        List<String> searchFields = new ArrayList<>();

        if (manufacturer != null && !manufacturer.trim().isEmpty()) {
            searchFields.add("openfda.manufacturer_name:\"" + manufacturer.trim() + "\"");
        }
        if (name != null && !name.trim().isEmpty()) {
            searchFields.add("products.brand_name:\"" + name.trim() + "\"");
        }
        if (searchFields.isEmpty()) {
            throw new BadRequestException("Missing manufacturer or brand name parameter");
        }
        String finalQuery = String.join("+AND", searchFields);
        int skipPage = (page - 1) * limit;
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("search", finalQuery)
                        .queryParam("limit", limit)
                        .queryParam("skip", skipPage)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }

}
