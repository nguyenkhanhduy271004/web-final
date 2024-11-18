package vn.nguyenduy.laptopshop.controller.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.nguyenduy.laptopshop.domain.chat.ChatMessage;
import vn.nguyenduy.laptopshop.service.chat.ChatService;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    // Serve the chat page
    @GetMapping("/chat")
    public String chatPage(Model model) {
        model.addAttribute("chats", chatService.getChatHistory(1, 2)); // Example: chat between user 1 and seller 2
        return "client/chat/chat";
    }
}