package com.stoi.eurekaserviceregistry.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "appconfig", ignoreUnknownFields = false)
public class AppConfigProperties {

    public AppConfigProperties() {

    }

    private Jwt jwt = new Jwt();
    private CorsConfiguration cors = new CorsConfiguration();
    private List<String> authRequest = new ArrayList<>();
    private List<String> permitAll = new ArrayList<>();
    private List<String> corsFilter = new ArrayList<>();
    private List<String> ignoredRequest = new ArrayList<>();

    public Jwt getJwt() {
        return jwt;
    }

    public void setJwt(Jwt jwt) {
        this.jwt = jwt;
    }

    public CorsConfiguration getCors() {
        return cors;
    }

    public void setCors(CorsConfiguration cors) {
        this.cors = cors;
    }

    public List<String> getAuthRequest() {
        return authRequest;
    }

    public void setAuthRequest(List<String> authRequest) {
        this.authRequest = authRequest;
    }

    public List<String> getPermitAll() {
        return permitAll;
    }

    public void setPermitAll(List<String> permitAll) {
        this.permitAll = permitAll;
    }

    public List<String> getCorsFilter() {
        return corsFilter;
    }

    public void setCorsFilter(List<String> corsFilter) {
        this.corsFilter = corsFilter;
    }

    public List<String> getIgnoredRequest() {
        return ignoredRequest;
    }

    public void setIgnoredRequest(List<String> ignoredRequest) {
        this.ignoredRequest = ignoredRequest;
    }

    public static class Jwt {
        private String secret;
        private String base64Secret;
        private long tokenValidityInSeconds;
        private long tokenValidityInSecondsForRememberMe;

        public Jwt() {
            this.secret = null;
            this.base64Secret = null;
            this.tokenValidityInSeconds = 86400L;
            this.tokenValidityInSecondsForRememberMe = 2592000L;
        }

        public String getSecret() {
            return this.secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getBase64Secret() {
            return this.base64Secret;
        }

        public void setBase64Secret(String base64Secret) {
            this.base64Secret = base64Secret;
        }

        public long getTokenValidityInSeconds() {
            return this.tokenValidityInSeconds;
        }

        public void setTokenValidityInSeconds(long tokenValidityInSeconds) {
            this.tokenValidityInSeconds = tokenValidityInSeconds;
        }

        public long getTokenValidityInSecondsForRememberMe() {
            return this.tokenValidityInSecondsForRememberMe;
        }

        public void setTokenValidityInSecondsForRememberMe(long tokenValidityInSecondsForRememberMe) {
            this.tokenValidityInSecondsForRememberMe = tokenValidityInSecondsForRememberMe;
        }
    }

}