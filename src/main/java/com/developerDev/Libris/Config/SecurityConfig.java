package com.developerDev.Libris.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {



    @Bean
    public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
            return http.authorizeHttpRequests(request->request
                    .requestMatchers("/public/**","/health-check").permitAll()
                    .anyRequest().authenticated())
                    .csrf(AbstractHttpConfigurer::disable)
                    .httpBasic(httpBasic->httpBasic.authenticationEntryPoint())
                    .build();

    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService();
        authenticationProvider().setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




}
