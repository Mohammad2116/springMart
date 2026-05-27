package ir.aspireapps.springmart.config;

import ir.aspireapps.springmart.error.InvalidTokenException;
import ir.aspireapps.springmart.security.CustomUserDetails;
import ir.aspireapps.springmart.security.CustomUserDetailsService;
import ir.aspireapps.springmart.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtConfig jwtConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String token;
        String username;
        if (authorizationHeader != null &&
                authorizationHeader.startsWith(jwtConfig.getTokenType())) {
            token = authorizationHeader.substring(7);
            try {
                username = jwtService.extractUsername(token);
                if (username != null) {
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        CustomUserDetails customUserDetails =
                                customUserDetailsService.loadUserByUsername(username);
                        if (jwtService.isTokenValid(token, customUserDetails.getUsername())) {
                            UsernamePasswordAuthenticationToken authToke =
                                    new UsernamePasswordAuthenticationToken(
                                            customUserDetails, null,
                                            customUserDetails.getAuthorities()
                                    );
                            authToke.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authToke);
                        }
                    }
                }
            } catch (Exception e) {
                throw new InvalidTokenException("Invalid token");
            }
        }
        filterChain.doFilter(request, response);
    }
}
