package ru.example.demoapp.config;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.example.demoapp.util.JWTUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerToken = request.getHeader("Authorization");
        if (isValidToken(headerToken)) {
                String tokenWithoutBearer = extractTokenFromHeader(headerToken);

                if (tokenWithoutBearer.isBlank()) {
                    setResponseStatus(response);
                } else {
                    handleValidToken(tokenWithoutBearer, response);
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
            setResponseStatus(response);
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

    private void setResponseStatus(HttpServletResponse response){
        response.setStatus(401);
    }
}
