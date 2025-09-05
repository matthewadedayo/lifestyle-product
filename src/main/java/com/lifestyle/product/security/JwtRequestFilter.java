package com.lifestyle.product.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtUtil;

    public JwtRequestFilter(JwtTokenUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        final String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            try {
                String username = jwtUtil.getUsernameFromToken(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Validate expiration
                    if (!jwtUtil.isTokenExpired(token)) {
                        Claims claims = Jwts.parserBuilder()
                                .setSigningKey(jwtUtil.getSecretKey())
                                .build()
                                .parseClaimsJws(token)
                                .getBody();

                        // ✅ Extract roles (make sure you add roles when generating token)
                        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                        Object roles = claims.get("roles");
                        if (roles instanceof List<?>) {
                            authorities = ((List<?>) roles).stream()
                                    .map(r -> new SimpleGrantedAuthority("ROLE_" + r.toString()))
                                    .collect(Collectors.toList());
                        }

                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(username, null, authorities);

                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            } catch (Exception e) {
                logger.error("Invalid JWT Token", e);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
