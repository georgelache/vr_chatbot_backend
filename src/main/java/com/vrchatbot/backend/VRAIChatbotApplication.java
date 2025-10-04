package com.vrchatbot.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VRAIChatbotApplication {
    public static void main(String[] args) {
        SpringApplication.run(VRAIChatbotApplication.class, args);
        System.out.println("✅ VR Chatbot Backend running on ws://localhost:8080/chat");
    }
}
