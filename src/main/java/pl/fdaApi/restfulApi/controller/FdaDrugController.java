package pl.fdaApi.restfulApi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.fdaApi.restfulApi.service.FdaApiService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/drug")
@RequiredArgsConstructor
public class FdaDrugController {

    private final FdaApiService fdaApiService;

    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> searchDrug(@RequestParam String manufacturer,
                                   @RequestParam(required = false) String name,
                                   @RequestParam(defaultValue = "1") int limit,
                                   @RequestParam(defaultValue = "1") int page) {
        return fdaApiService.searchDrug(manufacturer, name, limit, page);
    }
}
