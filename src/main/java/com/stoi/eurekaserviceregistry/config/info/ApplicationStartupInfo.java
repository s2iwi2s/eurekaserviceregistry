package com.stoi.eurekaserviceregistry.config.info;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

@Component
public class ApplicationStartupInfo implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Environment env;
    private BuildProperties buildProp;

    private GitProperties gitProp;

    public ApplicationStartupInfo(Environment env, BuildProperties buildProp, GitProperties gitProp){
        this.env = env;
        this.buildProp = buildProp;
        this.gitProp = gitProp;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        buildInfo();
        eurekaInfo();
        appInfo();
    }
    private void appInfo() {
        String protocol = Optional.ofNullable(env.getProperty("server.ssl.key-store")).map(key -> "https").orElse("http");
        String serverPort = env.getProperty("server.port");
        String contextPath = Optional
                .ofNullable(env.getProperty("server.servlet.context-path"))
                .filter(StringUtils::isNotBlank)
                .orElse("/");
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }

        StringBuilder build = new StringBuilder();
        build.append("\n----------------------------------------------------------");
        build.append("\n\t::::: Application '{}::{}' is running! :::::");
        build.append("\n\tLocal: \t\t{}://localhost:{}{}");
        build.append("\n\tExternal: \t{}://{}:{}{}");
        build.append("\n\tSwagger: \t{}://localhost:{}{}admin/swagger-ui");
        build.append("\n\tEureka: \t{}://localhost:{}{}admin/eureka");
        build.append("\n\tProfile(s): \t{}");
        build.append("\n\tPowered by Spring Boot {}");
        build.append("\n----------------------------------------------------------");

        log.info(build.toString(),
                env.getProperty("spring.application.name"), env.getProperty("application.version"),
                protocol, serverPort, contextPath,
                protocol, hostAddress, serverPort, contextPath,
                protocol, serverPort, contextPath,
                protocol, serverPort, contextPath,
                env.getActiveProfiles(),
                buildProp.get("spring-boot.version")
        );
    }

    private void buildInfo() {
        StringBuilder build = new StringBuilder();
        build.append("\n----------------------------------------------------------");
        build.append("\n\t::::: BUILD INFORMATION :::::");
        build.append("\n----------------------------------------------------------");
        build.append("\n\t=> Version: \t{}");
        build.append("\n\t=> Build Time: \t{}");
        build.append("\n\t=> Commit Time: {}");
        build.append("\n\t=> Branch: \t{}");
        build.append("\n\t=> CommitId: \t{}");
        build.append("\n----------------------------------------------------------");

        DateTimeFormatter fmt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                .withZone(ZoneId.systemDefault());

        log.info(build.toString(),
                buildProp.getVersion(),
                fmt.format(buildProp.getTime()),
                fmt.format(gitProp.getCommitTime()),
                gitProp.getBranch(),
                gitProp.getShortCommitId()
        );
    }

    private void eurekaInfo() {
        StringBuilder build = new StringBuilder();
        build.append("\n----------------------------------------------------------");
        build.append("\n\t::::: EUREKA INFORMATION :::::");
        build.append("\n----------------------------------------------------------");
        build.append("\n\t=> Default Zone: \t{}");
        build.append("\n\t=> Host Name: \t\t{}");
        build.append("\n\t=> Port: \t\t{}");
        build.append("\n\t=> Instance ID: \t{}");
        build.append("\n----------------------------------------------------------");

        log.info(build.toString(),
                env.getProperty("eureka.client.service-url.defaultZone"),
                env.getProperty("eureka.instance.hostname"),
                env.getProperty("eureka.instance.non-secure-port"),
                env.getProperty("eureka.instance.instance-id")
        );
    }
}
