package pl.fdaApi.restfulApi.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pl.fdaApi.restfulApi.exception.BadRequestException;
import pl.fdaApi.restfulApi.service.FdaApiService;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static reactor.core.publisher.Mono.when;

@WebMvcTest(FdaDrugController.class)
@ExtendWith(SpringExtension.class)
public class FdaDrugControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FdaApiService fdaApiService;

    @Test
    void searchDrug_ValidParameters() throws Exception {
        String result = "{\"result\": \"test\"}";

        when(fdaApiService.searchDrug(anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(Mono.just(result));

        mockMvc.perform(get("/drug/search")
                        .param("manufacturer", "Manufacturer")
                        .param("name", "Name")
                        .param("limit", "1")
                        .param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(result));
    }

    @Test
    void searchDrug_InvalidParams() throws Exception {

        when(fdaApiService.searchDrug(anyString(), nullable(String.class), anyInt(), anyInt()))
                .thenReturn(Mono.error(new BadRequestException("Bad request - missing params")));


        mockMvc.perform((get("/drug/search"))
                        .param("manufacturer", ""))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Bad request - missing params"));
    }
}
