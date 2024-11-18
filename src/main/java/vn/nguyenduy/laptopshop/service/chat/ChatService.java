package vn.nguyenduy.laptopshop.service.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.nguyenduy.laptopshop.domain.chat.ChatMessage;
import vn.nguyenduy.laptopshop.repository.chat.ChatRepository;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    // Save a new chat message
    public void saveMessage(ChatMessage chatMessage) {
        chatRepository.save(chatMessage);
    }

    // Retrieve chat history between two users
    public List<ChatMessage> getChatHistory(Integer senderId, Integer receiverId) {
        return chatRepository.findBySenderIdAndReceiverId(senderId, receiverId);
    }
}
