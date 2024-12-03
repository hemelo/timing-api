package org.hemelo.timing.config.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hemelo.timing.config.ws.handlers.TimeSpentHandshakeHandler;
import org.hemelo.timing.property.TimingProperties;
import org.hemelo.timing.service.TimeSpentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private TimingProperties timingProperties;

    @Autowired
    private TimeSpentService timeSpentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new TimeSpentHandshakeHandler(objectMapper, timeSpentService), "/timing-ws")
                .setAllowedOrigins(timingProperties.getCors().getAllowedOrigins().toArray(new String[0]));
    }
}