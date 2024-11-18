// package vn.nguyenduy.laptopshop.service;

// import java.util.Optional;

// import org.springframework.stereotype.Service;

// import lombok.RequiredArgsConstructor;
// import vn.nguyenduy.laptopshop.domain.ChatRoom;
// import vn.nguyenduy.laptopshop.repository.ChatRoomRepository;

// @Service
// @RequiredArgsConstructor
// public class ChatRoomService {
// private final ChatRoomRepository chatRoomRepository;

// public Optional<String> getChatRoomId(
// String senderId,
// String recipientId,
// boolean createNewRoomIfNotExists) {
// return chatRoomRepository
// .findBySenderIdAndRecipientId(senderId, recipientId)
// .map(ChatRoom::getChatId)
// .or(() -> {
// if (createNewRoomIfNotExists) {
// var chatId = createChatId(senderId, recipientId);
// return Optional.of(chatId);
// }

// return Optional.empty();
// });
// }

// private String createChatId(String senderId, String recipientId) {
// var chatId = String.format("%s_%s", senderId, recipientId);

// ChatRoom senderRecipient = ChatRoom
// .builder()
// .chatId(chatId)
// .senderId(senderId)
// .recipientId(recipientId)
// .build();

// ChatRoom recipientSender = ChatRoom
// .builder()
// .chatId(chatId)
// .senderId(recipientId)
// .recipientId(senderId)
// .build();

// this.chatRoomRepository.save(senderRecipient);
// this.chatRoomRepository.save(recipientSender);

// return chatId;
// }
// }
