package com.stoi.eurekaserviceregistry;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableDiscoveryClient
@EnableScheduling
@EnableAdminServer
@EnableEurekaServer
@SpringBootApplication
public class EurekaserviceregistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaserviceregistryApplication.class, args);
    }
}
