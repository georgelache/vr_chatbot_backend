package com.vrchatbot.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.*;
import java.nio.charset.StandardCharsets;

public class AIService {

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = System.getenv("OPENAI_API_KEY");

    private static final String appDescription = "Satellite observations reveal insights about our dynamic home planet to scientists," +
            " but people without a remote sensing background often find the stories within these datasets difficult to access. " +
            "NASA’s open data policy makes these observations available to everyone—scientists and non-scientists alike. " +
            "Your challenge is to build a short, immersive, virtual reality (VR) experience that leverages NASA’s Earth" +
            " observation datasets and visualizations to bring stories about our planet’s oceans to life, connecting a broad audience to this data, " +
            "its beauty, and its impact. Using visuals, spatial audio, and even interactive elements, your VR experience can enable users " +
            "to dive deeper into Earth’s unfolding ocean story. (Earth Science Division)";

    public static String askChatbot(String userInput) throws Exception {
        if (API_KEY == null) {
            throw new IllegalStateException("Missing OPENAI_API_KEY environment variable.");
        }

        String body = String.format(
                "{\"model\": \"gpt-3.5-turbo\"," +
                    "\"messages\": [" +
                        "{\"role\": \"system\"," +
                        "\"content\": \"You are a friendly VR guide helping the user navigate virtual environments. " +
                            "Here is the application description %s. " +
                        "    Answer in less than 100 words. \"}," +
                        "{\"role\": \"user\", \"content\": \"%s\"}]}", appDescription, userInput);

        System.out.println("Request Body: " + body); // Debugging line to print the request body

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Response Status Code: " + response.statusCode()); // Debugging line to print status code
        System.out.println("Response Body: " + response.body()); // Debugging line to print

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());
        return root.get("choices").get(0).get("message").get("content").asText();
    }
}
