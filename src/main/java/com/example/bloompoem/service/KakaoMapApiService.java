package com.example.bloompoem.service;

import com.example.bloompoem.domain.dto.RestKakaoPlaceResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("classpath:app.properties")
@RequiredArgsConstructor
public class KakaoMapApiService {
    @Value("#{environment['kakao.admin']}")
    private String adminKey;

    private final String apiUrl = "https://dapi.kakao.com/v2/local/search/keyword.json";

    public String searchPlace() throws IOException, InterruptedException {
        String query = "인천";
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String queryString = String.format(encodedQuery);

        URL url = new URL(apiUrl + "?" + queryString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization",adminKey);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    public List<RestKakaoPlaceResponse> outRestKakaoPlace(String query) throws JsonProcessingException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", adminKey);

        URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("query", query)
                .queryParam("page",1)
                .queryParam("size",1)
                .queryParam("category_group_code","PO3")
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
        List<RestKakaoPlaceResponse> placeRes = new ArrayList<>();

        for (JsonNode documentNode : documentsNode) {
            String name = documentNode.path("place_name").asText();
            String address = documentNode.path("address_name").asText();
            double lat = documentNode.path("y").asDouble();
            double lng = documentNode.path("x").asDouble();
            RestKakaoPlaceResponse place = new RestKakaoPlaceResponse(name, address, lat, lng);
            placeRes.add(place);
        }
        return placeRes;
    }
}
