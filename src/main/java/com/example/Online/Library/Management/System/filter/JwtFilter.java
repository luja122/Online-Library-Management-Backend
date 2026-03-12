package com.example.Online.Library.Management.System.filter;

import com.example.Online.Library.Management.System.service.JwtService;
import com.example.Online.Library.Management.System.service.MyUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService service;
    @Autowired
    private ApplicationContext context;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
     String auth = request.getHeader("Authorization");
     String Token = null;
     String gmail = null;
     if(auth!=null && auth.startsWith("Bearer ")){
         Token = auth.substring(7);
         gmail = service.extractGmail(Token);
     }
        if(gmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = context.getBean(MyUserDetails.class)
                    .loadUserByUsername(gmail);

            System.out.println("Authorities: " + userDetails.getAuthorities());
            System.out.println("Token valid: " + service.validateToken(Token, userDetails));
            System.out.println("Request URI: " + request.getRequestURI());

            if(service.validateToken(Token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                System.out.println("Auth set! Roles: " + userDetails.getAuthorities());
            }
        }
     filterChain.doFilter(request,response);
    }
}
