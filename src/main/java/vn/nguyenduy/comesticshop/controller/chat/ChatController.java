package vn.nguyenduy.comesticshop.controller.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.nguyenduy.comesticshop.service.chat.ChatService;

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