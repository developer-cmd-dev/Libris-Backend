package com.developerDev.Libris.Config;


import com.developerDev.Libris.AuthenticationFailureHandler.CustomAccessDeniedBearerToken;
import com.developerDev.Libris.AuthenticationFailureHandler.CustomBasicAuthenticationEntryPoint;
import com.developerDev.Libris.AuthenticationFailureHandler.CustomBearerAuthenticationEntryPoint;
import com.developerDev.Libris.Service.UserDetailServiceImpl;
import com.developerDev.Libris.filter.JWTFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailServiceImpl userDetailService;
    private final JWTFilter jwtFilter;
    private final CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint;
    private final CustomBearerAuthenticationEntryPoint customBearerAuthenticationEntryPoint;
    private final CustomAccessDeniedBearerToken customAccessDeniedBearerToken;
    public SecurityConfig(UserDetailServiceImpl userDetailService,
                          CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint, JWTFilter jwtFilter, CustomBearerAuthenticationEntryPoint customBearerAuthenticationEntryPoint, CustomAccessDeniedBearerToken customAccessDeniedBearerToken){
        this.customBasicAuthenticationEntryPoint = customBasicAuthenticationEntryPoint;
        this.userDetailService =userDetailService;
        this.jwtFilter = jwtFilter;
        this.customBearerAuthenticationEntryPoint = customBearerAuthenticationEntryPoint;
        this.customAccessDeniedBearerToken = customAccessDeniedBearerToken;
    }



    @Bean
    public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.authorizeHttpRequests(request -> request
                        .requestMatchers("/public/**","/health-check","/home").permitAll()
                        .requestMatchers("/user/**").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(httpSecurity->httpSecurity.authenticationEntryPoint(customBearerAuthenticationEntryPoint).accessDeniedHandler(customAccessDeniedBearerToken))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();


    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }



}
