package com.learning.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.concurrent.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class HttpClientUtil {
    private static final String BASE_URL = "http://localhost:8080";
    private static final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private static String token;

    public static void setToken(String token) {
        HttpClientUtil.token = token;
    }

    public static String getToken() {
        return token;
    }

    public static CompletableFuture<HttpResponse<String>> get(String endpoint) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(30))
                .GET();
        
        // 只在token不为空时添加Authorization header
        if (token != null && !token.trim().isEmpty()) {
            String authHeader = "Bearer " + token;
            builder.header("Authorization", authHeader);
            System.out.println("GET request with Authorization: " + authHeader.substring(0, Math.min(50, authHeader.length())) + "...");
        } else {
            System.out.println("GET request WITHOUT token - token value: " + token);
        }
        
        HttpRequest request = builder.build();
        
        System.out.println("GET request to: " + BASE_URL + endpoint);
        System.out.println("All headers: " + request.headers().map());
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .whenComplete((response, throwable) -> {
                    if (throwable != null) {
                        System.err.println("GET request failed: " + throwable.getMessage());
                        throwable.printStackTrace();
                    } else {
                        System.out.println("GET response: " + response.statusCode() + " - " + response.body());
                    }
                });
    }

    public static CompletableFuture<HttpResponse<String>> post(String endpoint, Object body) {
        try {
            String jsonBody = objectMapper.writeValueAsString(body);
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + endpoint))
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofSeconds(30))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody));
            
            // 只在token不为空时添加Authorization header
            if (token != null && !token.trim().isEmpty()) {
                String authHeader = "Bearer " + token;
                builder.header("Authorization", authHeader);
                System.out.println("POST request with Authorization: " + authHeader.substring(0, Math.min(50, authHeader.length())) + "...");
            } else {
                System.out.println("POST request WITHOUT token - token value: " + token);
            }
            
            HttpRequest request = builder.build();
            
            System.out.println("POST request to: " + BASE_URL + endpoint);
            System.out.println("POST body: " + jsonBody);
            System.out.println("All headers: " + request.headers().map());
            
            return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .whenComplete((response, throwable) -> {
                        if (throwable != null) {
                            System.err.println("POST request failed: " + throwable.getMessage());
                            throwable.printStackTrace();
                        } else {
                            System.out.println("POST response: " + response.statusCode() + " - " + response.body());
                        }
                    });
        } catch (Exception e) {
            System.err.println("Failed to serialize request body: " + e.getMessage());
            throw new RuntimeException("Failed to serialize request body", e);
        }
    }

    public static CompletableFuture<HttpResponse<String>> put(String endpoint, Object body) {
        try {
            String jsonBody = objectMapper.writeValueAsString(body);
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + endpoint))
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofSeconds(30))
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonBody));
            
            // 只在token不为空时添加Authorization header
            if (token != null && !token.trim().isEmpty()) {
                String authHeader = "Bearer " + token;
                builder.header("Authorization", authHeader);
                System.out.println("PUT request with Authorization: " + authHeader.substring(0, Math.min(50, authHeader.length())) + "...");
            } else {
                System.out.println("PUT request WITHOUT token - token value: " + token);
            }
            
            HttpRequest request = builder.build();
            System.out.println("PUT request to: " + BASE_URL + endpoint);
            System.out.println("PUT body: " + jsonBody);

            return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .whenComplete((response, throwable) -> {
                        if (throwable != null) {
                            System.err.println("PUT request failed: " + throwable.getMessage());
                            throwable.printStackTrace();
                        } else {
                            System.out.println("PUT response: " + response.statusCode() + " - " + response.body());
                        }
                    });
        } catch (Exception e) {
            System.err.println("Failed to serialize PUT request body: " + e.getMessage());
            throw new RuntimeException("Failed to serialize request body", e);
        }
    }

    public static CompletableFuture<HttpResponse<String>> delete(String endpoint) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(30))
                .DELETE();
        
        // 只在token不为空时添加Authorization header
        if (token != null && !token.trim().isEmpty()) {
            String authHeader = "Bearer " + token;
            builder.header("Authorization", authHeader);
            System.out.println("DELETE request with Authorization: " + authHeader.substring(0, Math.min(50, authHeader.length())) + "...");
        } else {
            System.out.println("DELETE request WITHOUT token - token value: " + token);
        }
        
        HttpRequest request = builder.build();
        System.out.println("DELETE request to: " + BASE_URL + endpoint);

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .whenComplete((response, throwable) -> {
                    if (throwable != null) {
                        System.err.println("DELETE request failed: " + throwable.getMessage());
                        throwable.printStackTrace();
                    } else {
                        System.out.println("DELETE response: " + response.statusCode() + " - " + response.body());
                    }
                });
    }

    public static <T> T parseResponse(String json, TypeReference<T> typeRef) {
        try {
            return objectMapper.readValue(json, typeRef);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse response", e);
        }
    }

    public static <T> Task<T> createApiTask(CompletableFuture<HttpResponse<String>> future, TypeReference<T> typeRef) {
        return new Task<>() {
            @Override
            protected T call() throws Exception {
                try {
                    System.out.println("Waiting for HTTP response...");
                    HttpResponse<String> response = future.get(30, java.util.concurrent.TimeUnit.SECONDS);
                    System.out.println("Received HTTP response: " + response.statusCode());
                    
                    if (response.statusCode() >= 200 && response.statusCode() < 300) {
                        String responseBody = response.body();
                        System.out.println("Response body: " + responseBody);
                        T result = parseResponse(responseBody, typeRef);
                        System.out.println("Parsed response successfully");
                        return result;
                    } else {
                        String errorMsg = "API call failed: " + response.statusCode() + " " + response.body();
                        System.err.println(errorMsg);
                        throw new RuntimeException(errorMsg);
                    }
                } catch (java.util.concurrent.TimeoutException e) {
                    System.err.println("Request timeout: " + e.getMessage());
                    throw new RuntimeException("Request timeout after 30 seconds", e);
                } catch (java.util.concurrent.ExecutionException e) {
                    System.err.println("Execution error: " + e.getMessage());
                    Throwable cause = e.getCause();
                    if (cause instanceof java.net.ConnectException) {
                        System.err.println("Connection refused: " + cause.getMessage());
                        throw new RuntimeException("Cannot connect to server at " + BASE_URL, cause);
                    }
                    throw new RuntimeException("API call execution failed", e);
                } catch (Exception e) {
                    System.err.println("Unexpected error: " + e.getMessage());
                    e.printStackTrace();
                    throw new RuntimeException("Unexpected error during API call", e);
                }
            }
        };
    }
}
