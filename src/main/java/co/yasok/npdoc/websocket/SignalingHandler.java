package co.yasok.npdoc.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class SignalingHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(SignalingHandler.class);
    private final Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
    private final Map<Integer, String> userSessionMap = new ConcurrentHashMap<>();
    private final Map<Integer, Integer> activeCalls = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = session.getUri().getQuery().split("=")[1];
        logger.info("uri query = " + session.getUri().getQuery());
        sessionMap.put(session.getId(), session);
        userSessionMap.put(Integer.parseInt(userId), session.getId());
        System.out.println("Connection established for user: " + userId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String sessionId = session.getId();
        Integer userId = userSessionMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(sessionId))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

        if (userId != null) {
            userSessionMap.remove(userId);
            sessionMap.remove(sessionId);
            Integer targetUserId = activeCalls.remove(userId);
            if (targetUserId != null) {
                sendCallEndedMessage(targetUserId, userId);
                activeCalls.remove(targetUserId);
            }
        }
        System.out.println("Connection closed: " + sessionId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        Map<String, Object> messageData = objectMapper.readValue(payload, Map.class);
        logger.info("Received text message: " + payload);
        String messageType = (String) messageData.get("type");
        Integer userId = (Integer) messageData.get("userId");
        Integer targetUserId = (Integer) messageData.get("targetUserId");
        logger.info("UserId = " + userId + ", targetUserId = " + targetUserId);
        if(userId != null && targetUserId != null) {
            switch (messageType) {
                case "startCall":
                    startCall(userId, targetUserId);
                    break;
                case "endCall":
                    endCall(userId, targetUserId);
                    break;
                case "message":
                case "callOffer":
                case "answer":
                case "iceCandidate":
                    forwardMessage(targetUserId, messageData);
                    break;
                default:
                    break;
            }
        }
    }

    private void startCall(Integer initiatingUser, Integer targetUser) {
        activeCalls.put(initiatingUser, targetUser);
        activeCalls.put(targetUser, initiatingUser);
        sendMessageToUser(targetUser, Map.of(
                "type", "callStarted",
                "initiatingUser", initiatingUser
        ));
        System.out.println("Call started between " + initiatingUser + " and " + targetUser);
    }

    private void endCall(Integer userId, Integer targetUserId) {
        activeCalls.remove(userId);
        activeCalls.remove(targetUserId);
        sendCallEndedMessage(targetUserId, userId);
        System.out.println("Call ended between " + userId + " and " + targetUserId);
    }

    private void forwardMessage(Integer targetUserId, Map<String, Object> messageData) {
        sendMessageToUser(targetUserId, messageData);
    }

    private void sendMessageToUser(Integer userId, Map<String, Object> messageData) {
        String sessionId = userSessionMap.get(userId);
        if(sessionId != null) {
            WebSocketSession session = sessionMap.get(sessionId);
            if (session != null && session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(objectMapper.writeValueAsString(messageData)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendCallEndedMessage(Integer targetUserId, Integer userId) {
        sendMessageToUser(targetUserId, Map.of(
                "type", "callEnded",
                "userId", userId
        ));
    }
}
