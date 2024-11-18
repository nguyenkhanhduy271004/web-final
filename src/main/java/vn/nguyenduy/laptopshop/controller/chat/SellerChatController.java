package vn.nguyenduy.laptopshop.controller.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.nguyenduy.laptopshop.service.chat.ChatService;

@Controller
public class SellerChatController {

    @Autowired
    private ChatService chatService;

    // Phục vụ giao diện chat của Seller
    @GetMapping("/seller/chat")
    public String sellerChatPage(Model model) {
        model.addAttribute("chats", chatService.getChatHistory(8, 35)); // Ví dụ: chat giữa seller 2 và buyer 1
        return "vendor/chat/seller-chat";
    }
}
