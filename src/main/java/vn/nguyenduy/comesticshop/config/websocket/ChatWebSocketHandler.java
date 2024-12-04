package vn.nguyenduy.comesticshop.config.websocket;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import java.util.Map;
import java.util.HashMap;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = (String) session.getAttributes().get("id");
        if (userId != null) {
            sessions.put(userId, session);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String userId = (String) session.getAttributes().get("id");
        if (userId != null) {
            // Gửi tin nhắn tới tất cả các client đã kết nối
            for (WebSocketSession wsSession : sessions.values()) {
                if (wsSession.isOpen()) {
                    wsSession.sendMessage(new TextMessage("Tin nhắn từ " + userId + ": " + message.getPayload()));
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = (String) session.getAttributes().get("id");
        if (userId != null) {
            sessions.remove(userId);
        }
    }
}
