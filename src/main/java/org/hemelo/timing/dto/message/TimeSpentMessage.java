package org.hemelo.timing.dto.message;

public record TimeSpentMessage(
        String type,
        String appId,
        Long timeOnPageMs,
        String pageName
){

    @Override
    public String toString() {
        return "TimeSpentMessage{" +
                "type='" + type + '\'' +
                ", appId='" + appId + '\'' +
                ", timeOnPageMs=" + timeOnPageMs +
                ", pageName='" + pageName + '\'' +
                '}';
    }
}
