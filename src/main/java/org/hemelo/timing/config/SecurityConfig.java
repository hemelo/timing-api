package org.hemelo.timing.config;

import org.hemelo.timing.filters.JwtRequestFilter;
import org.hemelo.timing.property.TimingProperties;
import org.hemelo.timing.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private TimingProperties timingProperties;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                authorizationManagerRequestMatcherRegistry
                        .requestMatchers("/timing-ws/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated();

            })
            .sessionManagement(sessionManagementConfigurer -> {
                sessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            })
            .httpBasic(AbstractHttpConfigurer::disable);

        TimingProperties.Cors corsProperties = timingProperties.getCors();

        http.cors(cors -> {
            cors.configurationSource(request -> {
                var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
                corsConfiguration.setAllowedOrigins(corsProperties.getAllowedOrigins());
                corsConfiguration.setAllowedMethods(corsProperties.getAllowedMethods());
                corsConfiguration.setAllowedHeaders(corsProperties.getAllowedHeaders());
                corsConfiguration.setExposedHeaders(corsProperties.getExposedHeaders());
                corsConfiguration.setAllowCredentials(corsProperties.isAllowCredentials());
                corsConfiguration.setMaxAge(corsProperties.getMaxAge());
                return corsConfiguration;
            });
        });

        http.csrf(AbstractHttpConfigurer::disable);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       PasswordEncoder passwordEncoder,
                                                       UserDetailsServiceImpl userDetailsService) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);

        return authManagerBuilder.build();
    }
}
