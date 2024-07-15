package co.yasok.npdoc.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

public class SignalingHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, WebSocketSession> sessions = new HashMap<>();
    private final Map<String, String> userIdToSessionIdMap = new HashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        Map<String, Object> jsonData = objectMapper.readValue(payload, Map.class);
        String userId = (String) jsonData.get("userId");
        String type = (String) jsonData.get("type");
        String target = (String) jsonData.get("target");

        // On receipt of the first message (of any type), associate the userId with the session ID
        if (!userIdToSessionIdMap.containsKey(userId)) {
            userIdToSessionIdMap.put(userId, session.getId());
        }

        handleOfferOrAnswerOrCandidate(type, target, jsonData, session);
    }

    private void handleOfferOrAnswerOrCandidate(String type, String target,
                                                Map<String, Object> data, WebSocketSession session) throws Exception {
        if ("offer".equals(type) || "answer".equals(type) || "candidate".equals(type)) {
            String targetSessionId = userIdToSessionIdMap.get(target);
            WebSocketSession targetSession = sessions.get(targetSessionId);
            if (targetSession != null && targetSession.isOpen()) {
                targetSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(data)));
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId());
        // Remove the userId -> sessionId mapping when the session closes
        String userIdToRemove = null;
        for (Map.Entry<String, String> entry : userIdToSessionIdMap.entrySet()) {
            if (entry.getValue().equals(session.getId())) {
                userIdToRemove = entry.getKey();
                break;
            }
        }
        if (userIdToRemove != null) {
            userIdToSessionIdMap.remove(userIdToRemove);
        }
    }
}