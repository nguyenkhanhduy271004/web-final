package vn.nguyenduy.comesticshop.repository.chat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.nguyenduy.comesticshop.domain.chat.ChatMessage;

public interface ChatRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findBySenderIdAndReceiverId(Integer senderId, Integer receiverId);
}
