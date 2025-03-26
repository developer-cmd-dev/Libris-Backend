package com.developerDev.Libris.filter;

import com.developerDev.Libris.Config.SecurityConfig;
import com.developerDev.Libris.Service.UserDetailServiceImpl;
import com.developerDev.Libris.Utils.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserDetailServiceImpl userDetailService;

    public JWTFilter(JWTUtil jwtUtil,UserDetailServiceImpl userDetailService){
        this.userDetailService=userDetailService;
        this.jwtUtil =jwtUtil;

    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;
        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
            jwt = authorizationHeader.substring(7);
            log.info(jwt);
            username = jwtUtil.extractUsername(jwt);
            log.info(username);
        }

        if(username!=null){

            UserDetails userDetails = userDetailService.loadUserByUsername(username);
            log.info(userDetails.getAuthorities().toString());
            if(Boolean.TRUE.equals(jwtUtil.validateToken(jwt))){
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,null,
                        userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request,response);

    }
}
