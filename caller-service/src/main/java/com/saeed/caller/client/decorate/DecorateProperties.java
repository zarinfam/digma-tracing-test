package com.saeed.caller.client.decorate;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "clients.decorate-service")
public record DecorateProperties(String url) {
}
