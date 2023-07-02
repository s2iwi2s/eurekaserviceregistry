package com.stoi.eurekaserviceregistry.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration {

    private final AdminServerProperties adminServer;

    public WebSecurityConfiguration(AdminServerProperties adminServer) {
        this.adminServer = adminServer;
    }


    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(this.adminServer.path("/"));

        http.authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers(new AntPathRequestMatcher(this.adminServer.path("/eureka/**"))).permitAll()
                        .requestMatchers(new AntPathRequestMatcher(this.adminServer.path("/assets/**"))).permitAll()
                        .requestMatchers(new AntPathRequestMatcher(this.adminServer.path("/login"))).permitAll()
                        .anyRequest().authenticated())

                .formLogin((formLogin) -> formLogin.loginPage(this.adminServer.path("/login"))
                        .successHandler(successHandler))
                .logout((logout) -> logout.logoutUrl(this.adminServer.path("/logout")))
                .httpBasic(Customizer.withDefaults())
                .csrf((csrf) -> csrf.disable());
//                .csrf((csrf) -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                        .ignoringRequestMatchers(
//                                new AntPathRequestMatcher(this.adminServer.path("/instances"), HttpMethod.POST.toString()),
//                                new AntPathRequestMatcher(this.adminServer.path("/instances/*"), HttpMethod.DELETE.toString()),
//                                new AntPathRequestMatcher(this.adminServer.path("/actuator/**")),
//                                new AntPathRequestMatcher(this.adminServer.path("/eureka/**"))
//                        ));

        return http.build();
    }

}
