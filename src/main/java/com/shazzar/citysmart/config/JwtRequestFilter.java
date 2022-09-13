package com.shazzar.citysmart.config;

import com.shazzar.citysmart.config.userauth.AppUserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final AppUserService userService;

    public JwtRequestFilter(JwtUtil jwtUtil, AppUserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader(AUTHORIZATION);

        String username = null;
        String jwt = null;
        String bearer = "Bearer ";

        if (requestTokenHeader != null && requestTokenHeader.startsWith(bearer)) {
            jwt = requestTokenHeader.substring(bearer.length());

            try {
                username = jwtUtil.getUserNameFromToken(jwt);
            } catch (IllegalArgumentException ex) {
                logger.error("An error occurred wile fetching username from token", ex);
            } catch (ExpiredJwtException ex) {
                logger.warn("The token has expired", ex);
            } catch (SignatureException ex) {
                logger.error("Authentication failed. Username or password not valid", ex);
            }
        } else {
            logger.warn("Couldn't find bearer string. header will be ignored");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);

    }
}
