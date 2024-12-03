package org.hemelo.timing.config.ws.sessions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.time.Instant;

@Getter
@Setter
public class TimeSpentWebSocketSession {

    private WebSocketSession session;
    private Instant startedAt;

    public TimeSpentWebSocketSession(WebSocketSession session) {
        this.session = session;
        this.startedAt = Instant.now();
    }
}
