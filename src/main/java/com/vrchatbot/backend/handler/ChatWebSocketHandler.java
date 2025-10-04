package com.vrchatbot.backend.handler;

import com.vrchatbot.backend.service.AIService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String userMessage = message.getPayload();
        System.out.println("👤 User: " + userMessage);

        String botReply;
        try {
            botReply = AIService.askChatbot(userMessage);
        } catch (Exception e) {
            botReply = "Sorry, something went wrong with the AI service.";
            e.printStackTrace();
        }

        session.sendMessage(new TextMessage(botReply));
        System.out.println("🤖 Bot: " + botReply);
    }
}
