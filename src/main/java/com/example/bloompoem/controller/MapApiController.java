package com.example.bloompoem.controller;

import com.example.bloompoem.domain.dto.PlaceRes;
import com.example.bloompoem.service.KakaoMapApiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@PropertySource("classpath:app.properties")
public class MapApiController {

    @Value("#{environment['kakao.admin']}")
    private String adminKey;

    private final KakaoMapApiService kakaoMapApiService;

    @GetMapping("/test/map")
    @ResponseBody
    public List<PlaceRes> searchPlace() throws Exception {
        String apiUrl = "https://dapi.kakao.com/v2/local/search/keyword.json";
        String query = "카카오프렌즈";


        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", adminKey);

        URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("query", query)
                .build()
                .encode()
                .toUri();

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        String responseBody = response.getBody();

        // Jackson ObjectMapper을 사용하여 json파싱
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseBody);

        // 필요한 정보 추출
        JsonNode documentsNode = rootNode.path("documents");
        List<PlaceRes> placeRes = new ArrayList<>();

        for (JsonNode documentNode : documentsNode) {
            String name = documentNode.path("place_name").asText();
            String address = documentNode.path("address_name").asText();
            double lat = documentNode.path("y").asDouble();
            double lng = documentNode.path("x").asDouble();
            PlaceRes place = new PlaceRes(name, address, lat, lng);
            placeRes.add(place);
        }
        return placeRes;
    }
}
