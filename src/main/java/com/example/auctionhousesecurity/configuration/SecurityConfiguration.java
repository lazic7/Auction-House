package com.example.auctionhousesecurity.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    //TODO: because of no sniff in HTTP headers policy, content type in forms is required, make sure of this in react app and axios setup
    //TODO: implement custom jwtAuthenticationFilter and make sure to " Use HttpSecurity#addFilterBefore to add the CustomFilter before the AuthorizationFilter."

    @Bean
    public SecurityFilterChain securityFilterChain(
            final HttpSecurity http
    ) throws Exception {

        http
                //don't use http basic, implementing custom filter
                .httpBasic(AbstractHttpConfigurer::disable)
                //don't use login form, implementing custom form
                .formLogin(AbstractHttpConfigurer::disable)
                //don't use csrf, application is authenticating and authorizing with JWT
                .csrf(AbstractHttpConfigurer::disable)
                .cors((cors) -> {
                    cors.configurationSource(corsConfigurationSource());
                })
                .headers((headers) -> {
                    //use default HTTP headers but,
                    //allow X-Frame-Options on same origin because of h2-console
                    headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin);
                })
                //don't store unauthenticated requests in cache
                .requestCache((cache) -> {
                    cache.requestCache(new NullRequestCache());
                })
                //don't create sessions, re-authenticate user on every request
                .sessionManagement((session) -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                //custom logout endpoint and onSuccess
                .logout((logout) -> {
                    logout
                            //custom logout url
                            .logoutUrl("/authentication/logout")
                            //return status code on success logout
                            .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
                })
                .authorizeHttpRequests((authorize) -> {
                    authorize
                            .requestMatchers("/h2-console/**", "/authentication/**", "/error/**", "/accounts/**").permitAll()
                            .anyRequest().authenticated();
                });

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH"));

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
