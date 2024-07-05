package com.udacity.jwdnd.c1.review.service;

import com.udacity.jwdnd.c1.review.mapper.MessageMapper;
import com.udacity.jwdnd.c1.review.model.ChatMessage;
import com.udacity.jwdnd.c1.review.model.MessageType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class MessageService {
//    private List<ChatMessage> chatMessages = new ArrayList<>();

//    public MessageService(String message) {
//        this.message = message;
//        System.out.println("[[[[[ MessageService: " + message);
//    }

//    @PostConstruct
//    public void postConstruct() {
//        chatMessages = new ArrayList<>();
//    }

    private MessageMapper messageMapper;

    public MessageService(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    public void addChatMessage(String userName, String messageText, MessageType messageType) {
        switch (messageType) {
            case SHOUT:
                messageMapper.insert(new ChatMessage(null, userName, messageText.toUpperCase()));
                break;
            case WHISPER:
                messageMapper.insert(new ChatMessage(null, userName, messageText.toLowerCase()));
                break;
            default:
                messageMapper.insert(new ChatMessage(null, userName, messageText));
                break;
        }
    }

    public List<ChatMessage> getChatMessages() {
        return messageMapper.getAllMessages();
    }

//    public String upperCase(String message) {
//        String upperCase = message.toUpperCase();
//        return upperCase;
//    }
//
//    public String lowerCase(String message) {
//        return message.toLowerCase();
//    }

}
