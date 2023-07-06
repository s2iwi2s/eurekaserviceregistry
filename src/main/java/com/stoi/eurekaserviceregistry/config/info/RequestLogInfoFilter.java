//package com.stoi.eurekaserviceregistry.config.info;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.web.util.ContentCachingRequestWrapper;
//import org.springframework.web.util.ContentCachingResponseWrapper;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//
//@Component
//@WebFilter(urlPatterns = "/*")
//@Order(-999)
//public class RequestLogInfoFilter extends OncePerRequestFilter {
//    private final Logger log = LoggerFactory.getLogger(this.getClass());
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper(request);
//        ContentCachingResponseWrapper resp = new ContentCachingResponseWrapper(response);
//
//        // Execution request chain
//        filterChain.doFilter(req, resp);
//
//        // Get Cache
//        byte[] requestBody = req.getContentAsByteArray();
//        byte[] responseBody = resp.getContentAsByteArray();
//
//        logRequest(request, requestBody);
//        logResponse(response, responseBody);
//
//        log.info("response body = {}", new String(responseBody, StandardCharsets.UTF_8));
//
//        // Finally remember to respond to the client with the cached data.
//        resp.copyBodyToResponse();
//    }
//
//
//    private void logRequest(HttpServletRequest request, byte[] body) throws IOException {
//        if (log.isDebugEnabled()) {
//            log.debug("===========================request begin================================================");
//            log.debug("URI         : {}", request.getRequestURI());
//            log.debug("Method: {}, Request: {}", request.getMethod(), new String(body, StandardCharsets.UTF_8));
//        }
//    }
//
//    private void logResponse(HttpServletResponse response, byte[] body) throws IOException {
//        if (log.isDebugEnabled()) {
//            log.debug("Status code  : {}", response.getStatus());
//            log.debug("Response body: {}", new String(body, StandardCharsets.UTF_8));
//            log.debug("=======================response end=================================================");
//        }
//    }
//}