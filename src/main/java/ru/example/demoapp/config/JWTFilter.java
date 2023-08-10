package ru.example.demoapp.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.example.demoapp.sevice.UserDetailServiceImpl;
import ru.example.demoapp.util.JWTUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final UserDetailServiceImpl userDetailService;

    @Autowired
    public JWTFilter(JWTUtil jwtUtil, UserDetailServiceImpl userDetailService) {
        this.jwtUtil = jwtUtil;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerToken = request.getHeader("Authorization");
        //TODO рефакторинг
        if (isValidToken(headerToken)) {
            if (!headerToken.isBlank()) {
                String tokenWithoutBearer = extractTokenFromHeader(headerToken);

                if (tokenWithoutBearer.isBlank()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT token");
                } else {
                    handleValidToken(tokenWithoutBearer, response);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromHeader(String headerToken) {
        return headerToken.substring(7);
    }

    private void handleValidToken(String tokenWithoutBearer, HttpServletResponse response) throws IOException {
        try {
            String claimUsername = jwtUtil.getClaim(tokenWithoutBearer);

            UserDetails userDetails = userDetailService.loadUserByUsername(claimUsername);

            UsernamePasswordAuthenticationToken authenticationToken = createAuthenticationToken(userDetails);

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (JWTVerificationException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "JWT Token not valid");
        }
    }

    private UsernamePasswordAuthenticationToken createAuthenticationToken(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
    }

    private boolean isValidToken(String token){
        return token != null && !token.isBlank() && token.startsWith("Bearer ");
    }
}
