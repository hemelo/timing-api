package org.hemelo.timing.config.ws.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hemelo.timing.config.ws.sessions.TimeSpentWebSocketSession;
import org.hemelo.timing.dto.message.TimeSpentMessage;
import org.hemelo.timing.dto.other.TimeSpentDto;
import org.hemelo.timing.service.TimeSpentService;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.time.Duration;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class TimeSpentHandshakeHandler implements WebSocketHandler {

    private final ObjectMapper objectMapper;
    private final TimeSpentService timeSpentService;

    private static Set<TimeSpentWebSocketSession> sessions = new HashSet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.debug("New session established: {}", session.getId());

        TimeSpentWebSocketSession timeSpentWebSocketSession = new TimeSpentWebSocketSession(session);
        sessions.add(timeSpentWebSocketSession);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.debug("Received message: {}", message.getPayload());

        try {
            TimeSpentMessage timeSpentMessage = objectMapper.readValue(message.getPayload().toString(), TimeSpentMessage.class);

            if (StringUtils.isBlank(timeSpentMessage.type())) {
                throw new IllegalArgumentException("Type is required");
            }

            if (StringUtils.isBlank(timeSpentMessage.appId())) {
                throw new IllegalArgumentException("AppId is required");
            }

            if (timeSpentMessage.type().equalsIgnoreCase("INIT")) {
                log.debug("Received INIT message: {}", timeSpentMessage);
            } else if (timeSpentMessage.type().equalsIgnoreCase("INSERT_TIME")) {
                log.debug("Received INSERT_TIME message: {}", timeSpentMessage);

                TimeSpentWebSocketSession timeSpentWebSocketSession = null;

                try {
                    timeSpentWebSocketSession = getSession(session);
                } catch (IllegalArgumentException e) {
                    log.error("Error getting session", e);
                }

                TimeSpentDto timeSpentDto = new TimeSpentDto(
                        timeSpentMessage.pageName(),
                        timeSpentMessage.appId(),
                        Optional.ofNullable(timeSpentWebSocketSession).map(TimeSpentWebSocketSession::getStartedAt).orElse(null),
                        Duration.ofMillis(timeSpentMessage.timeOnPageMs())
                );

                timeSpentService.insertTimeSpent(timeSpentDto);

            } else {
                throw new IllegalArgumentException("Non recognized type: " + timeSpentMessage.type());
            }

        } catch (Exception e) {
            log.error("Error parsing message: {}", message.getPayload(), e);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("Error in session: {}", session.getId(), exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.debug("Session closed: {}", session.getId());

        try {
            sessions.remove(getSession(session));
        } catch (IllegalArgumentException e) {
            log.error("Error removing session", e);
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private TimeSpentWebSocketSession getSession(WebSocketSession session) {
        return sessions.stream()
                .filter(s -> s.getSession().getId().equals(session.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));
    }
}
