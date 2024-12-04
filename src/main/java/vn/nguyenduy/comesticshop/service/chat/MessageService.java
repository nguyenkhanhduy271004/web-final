// package vn.nguyenduy.laptopshop.service.chat;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.messaging.simp.SimpMessagingTemplate;
// import org.springframework.stereotype.Service;

// import vn.nguyenduy.laptopshop.domain.chat.MessageDTO;

// @Service
// public class MessageService {

// @Autowired
// private SimpMessagingTemplate simpMessagingTemplate;

// @Autowired
// private MessageRepository messageRepository;

// @Autowired
// private GroupMessageRepository groupMessageRepository;

// public void sendMessage(String to, MessageDTO messageDTO) {
// Message message = new Message();
// message.setMessageText(messageDTO.getMessage());
// message.setMessageFrom(messageDTO.getFromLogin());
// message.setMessageTo(Integer.parseInt(to));
// message.setCreatedDatetime(LocalDateTime.now());

// messageRepository.save(message);
// simpMessagingTemplate.convertAndSend("/topic/messages/" + to, messageDTO);
// }

// public List<Message> getListMessage(Integer from, Integer to) {
// return messageRepository.findMessagesBetween(from, to);
// }

// public List<GroupMessage> getListMessageGroups(Integer groupId) {
// return groupMessageRepository.findMessagesByGroup(groupId);
// }

// public void sendMessageGroup(Integer to, MessageGroupDTO messageDTO) {
// GroupMessage groupMessage = new GroupMessage();
// groupMessage.setGroupId(to);
// groupMessage.setUserId(messageDTO.getFromLogin());
// groupMessage.setMessages(messageDTO.getMessage());
// groupMessage.setCreatedDatetime(LocalDateTime.now());

// groupMessageRepository.save(groupMessage);
// simpMessagingTemplate.convertAndSend("/topic/messages/group/" + to,
// messageDTO);
// }
// }