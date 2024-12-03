package org.hemelo.timing.dto.request;

public record AuthenticationRequest (
    String username,
    String password
){}
