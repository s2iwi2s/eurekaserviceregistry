package com.stoi.eurekaserviceregistry.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final AdminServerProperties adminServer;

    private final AppConfigProperties appConfig;

    private final String adminContextPath;

    public WebSecurityConfiguration(AdminServerProperties adminServer, AppConfigProperties appConfig) {
        this.adminServer = adminServer;
        this.appConfig = appConfig;
        this.adminContextPath = adminServer.getContextPath();

    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(this.adminServer.path("/"));
        http.authorizeHttpRequests((authorizeRequests) -> {
            log.debug("Registering Authorized Http Requests - permitAll pattern:");
            for (String pattern : appConfig.getPermitAll()) {
                log.debug("\t - {}", pattern);
                authorizeRequests.requestMatchers(new AntPathRequestMatcher(this.adminServer.path(pattern))).permitAll();
            }
            authorizeRequests.anyRequest().authenticated();
        });
        log.debug("Registering ignored requests pattern:");
        appConfig.getIgnoredRequest().forEach(pattern -> log.debug("\t - {}", pattern));
        http.formLogin((formLogin) -> formLogin.loginPage(this.adminServer.path("/login"))
                        .successHandler(successHandler))
                .logout((logout) -> logout.logoutUrl(this.adminServer.path("/logout")))
                .httpBasic(Customizer.withDefaults())
//                .csrf((csrf) -> csrf.disable());
                .csrf((csrf) -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers(appConfig.getIgnoredRequest().toArray(String[]::new))
                );

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = appConfig.getCors();
        if (!CollectionUtils.isEmpty(config.getAllowedOrigins()) || !CollectionUtils.isEmpty(config.getAllowedOriginPatterns())) {
            log.debug("Registering CORS filter pattern");
            for (String pattern : appConfig.getCorsFilter()) {
                log.debug("\t - {}", pattern);
                source.registerCorsConfiguration(pattern, config);
            }
        }
        return new CorsFilter(source);
    }

}
