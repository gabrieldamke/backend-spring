package com.br.edu.utfpr.trabalho_backend_topicosavancados.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {

        @Autowired
        private UserAuthenticationFilter userAuthenticationFilter;

        public static final String[] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
                        "/pessoa/login",
                        "/pessoa",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/pessoa/login",
                        "/swagger-ui/index.html",
                        "/swagger-ui/v3/api-docs/**",
                        "/v2/api-docs/**",
                        "/v3/swagger.json",
                        "swagger-ui/v3/swagger.json",
                        "/v2/swagger.json",
                        "/swagger-ui/swagger-initializer.js",
                        "/swagger-ui/swagger-ui-standalone-preset.js",
                        "/swagger-ui/swagger-ui-bundle.js",
                        "/swagger-ui/index.css",
                        "/swagger-ui/swagger-ui.css",
                        "/webjars/**",
                        "/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/v3/api-docs/swagger-config",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/webjars/**",
                        "/v3/**"
        };

        public static final String[] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
                        "/users/test"
        };

        public static final String[] ENDPOINTS_CUSTOMER = {
                        "/users/test/customer"
        };

        public static final String[] ENDPOINTS_ADMIN = {
                        "/users/test/administrator"
        };

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
                return httpSecurity
                                .csrf(csrf -> csrf.disable())
                                .cors(cors -> cors.disable())
                                .sessionManagement(management -> management
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(requests -> requests
                                                .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                                                .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
                                                .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMINISTRATOR")
                                                .requestMatchers(ENDPOINTS_CUSTOMER).hasRole("CUSTOMER")
                                                .anyRequest().denyAll())
                                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                                .build();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}