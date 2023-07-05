package com.stoi.eurekaserviceregistry.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "appconfig", ignoreUnknownFields = false)
public class AppConfigProperties {
    private final CorsConfiguration cors = new CorsConfiguration();

    private final List<String> permitAll = new ArrayList<>();

    private final List<String> corsFilter = new ArrayList<>();

    private final List<String> ignoredRequest = new ArrayList<>();

    public CorsConfiguration getCors() {
        return cors;
    }

    public List<String> getCorsFilter() {
        return corsFilter;
    }

    public List<String> getPermitAll() {
        return permitAll;
    }

    public List<String> getIgnoredRequest() {
        return ignoredRequest;
    }
}
