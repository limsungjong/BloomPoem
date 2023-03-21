package com.example.bloompoem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;

@Service
@PropertySource("classpath:app.properties")
public class KakaoMapApiService {
    @Value("#{environment['kakao.admin']}")
    private String adminKey;

    private final String apiUrl = "https://dapi.kakao.com/v2/local/search/keyword.json";

    private final HttpClient httpClient;

    public KakaoMapApiService() {
        this.httpClient = HttpClient.newBuilder().build();
    }

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
}
