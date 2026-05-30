package com.example.addiction_tracker.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class QuoteService {

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getDollarRate() {
        Request request = new Request.Builder()
                .url("https://open.er-api.com/v6/latest/USD")
                .build();
        try (Response response = client.newCall(request).execute()) {
            String body = response.body().string();
            JsonNode node = objectMapper.readTree(body);
            double rub = node.get("rates").get("RUB").asDouble();
            return String.format("%.2f", rub);
        } catch (Exception e) {
            return "недоступно";
        }
    }
}