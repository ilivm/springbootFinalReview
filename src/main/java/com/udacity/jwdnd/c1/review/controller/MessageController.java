package com.udacity.jwdnd.c1.review.controller;

import com.udacity.jwdnd.c1.review.model.ChatForm;
import com.udacity.jwdnd.c1.review.model.MessageType;
import com.udacity.jwdnd.c1.review.model.User;
import com.udacity.jwdnd.c1.review.service.MessageService;
import com.udacity.jwdnd.c1.review.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class MessageController {
    Logger logger = LoggerFactory.getLogger(MessageController.class);

    private MessageService messageService;
    private UserService userService;

    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @GetMapping
    public String getChatHomepage(ChatForm chatForm, Model model) {
//        Object userObject = model.getAttribute("user");
//        logger.info("userObject: " + userObject);
//        if (userObject instanceof User) {
//            logger.info("logged Username: " + ((User) userObject).getUserName() );
//        }
        model.addAttribute("chatMessages", messageService.getChatMessages());
        return "chat";
    }

    @PostMapping
    public String addChatMessage(ChatForm chatForm, Model model) {
        messageService.addChatMessage(getCurrentUsername(), chatForm.getMessageText(),
                chatForm.getMessageType());
        model.addAttribute("chatMessages", messageService.getChatMessages());
        chatForm.setUserName("");
        chatForm.setMessageText("");
        chatForm.setMessageType(MessageType.SAY);
        return "chat";
    }

    @ModelAttribute("username")
    public String getCurrentUsername() {
        return userService.getCurrentUsername();
    }

    @ModelAttribute("MessageTypes")
    public MessageType[] getMessageTypes() {
        return MessageType.values();
    }

//    @Bean("upperCaseMessage")
//    public String upperCaseMessage(MessageService messageService, String message) {
//        System.out.println("[[[[[ upperCase Message: " + messageService.getMessage());
//        return messageService.upperCase(message);
//    }
//
//    @Bean("lowerCaseMessage")
//    public String lowerCaseMessage(MessageService messageService, String upperCaseMessage) {
//        System.out.println("[[[[[ lowerCase Message: " + upperCaseMessage);
//        return messageService.lowerCase(upperCaseMessage);
//    }
}
