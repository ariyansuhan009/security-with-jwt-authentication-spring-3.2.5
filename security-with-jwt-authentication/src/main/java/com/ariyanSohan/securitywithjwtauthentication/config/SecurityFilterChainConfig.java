package com.ariyanSohan.securitywithjwtauthentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityFilterChainConfig {
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    public SecurityFilterChainConfig(AuthenticationEntryPoint authenticationEntryPoint, JWTAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

//    private static final String[] SWAGGER_WHITELIST = {
//      "/swagger-ui/**",
//      "/v3/api-docs/**",
//      "swagger-resources/**",
//      "swagger-resources"
//    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(corsConfig->corsConfig.configurationSource(getConfigurationSource()));

        // Disable CSRF
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        //httpSecurity.cors(corsConfig -> corsConfig.disable());

        // Http Request Filter
        httpSecurity.authorizeHttpRequests(
                requestMatcher ->
                        requestMatcher.requestMatchers("/api/auth/authenticate/**").permitAll()
                                .requestMatchers("/api/auth/sign-up/**").permitAll()
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                                .anyRequest().authenticated()
        );

        // Authentication Entry Point -> Exception Handler
        httpSecurity.exceptionHandling(
                exceptionConfig -> exceptionConfig.authenticationEntryPoint(authenticationEntryPoint)
        );

        // Set session policy = STATELESS
        httpSecurity.sessionManagement(
                sessionConfig->sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // Add JWT Authentication Filter
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    private static CorsConfigurationSource getConfigurationSource(){
        var corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000/", "http://localhost:8080"));
        corsConfiguration.setAllowedHeaders(List.of("Content-Type"));

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return  source;
    }
}



// ----------------- AlterNative Conifuguration---------------------
// Alternative
//@Bean
//public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    return http.cors(AbstractHttpConfigurer::disable)
//            .csrf(AbstractHttpConfigurer::disable)
//            .exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer.authenticationEntryPoint(unauthorizedHandler))
//            .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
//                        try {
//                            authorizationManagerRequestMatcherRegistry
//                                    .requestMatchers(HttpMethod.POST, POST_AUTH_WHITELIST).permitAll()
//                                    .requestMatchers(HttpMethod.GET, GET_AUTH_WHITELIST).permitAll()
//                                    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
//                                    .anyRequest()
//                                    .authenticated()
//                                    .and()
//                                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//                        } catch (Exception e) {
//                            throw new ResourceNotFoundException(e.getMessage());
//                        }
//                    }
//            )
//            .formLogin(AbstractHttpConfigurer::disable)
//            .httpBasic(AbstractHttpConfigurer::disable).addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//            .authenticationProvider(daoAuthenticationProvider()).build();
//}