package com.cinema.auth;

import com.cinema.api.model.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JwtAuthFilter.
 */
public class JwtAuthFilter extends UsernamePasswordAuthenticationFilter {

    private final static Logger LOG = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestUri = httpRequest.getRequestURI();
        if (requestUri.startsWith("/private")) {

            String authToken = httpRequest.getHeader("Authorization");

            User userPrincipal;
            try {
                userPrincipal = JwtUtil.parseJWT(authToken);
            } catch (ExpiredJwtException eje) {
                prepareErrorResponse(httpResponse, "Auth Token is expired.");
                return;
            }

            if (userPrincipal == null) {
                prepareErrorResponse(httpResponse, "Wrong Auth Token");
                filterChain.doFilter(request, response);
            } else {
                Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null,
                        userPrincipal.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void prepareErrorResponse(HttpServletResponse httpResponse, String errorMessage) {
        ErrorResponse response = new ErrorResponse(errorMessage);
        try {
            httpResponse.setContentType("application/json");
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getOutputStream().println(mapper.writeValueAsString(response));
        } catch (IOException e) {
            LOG.error("IOException: ", e);
        }
    }

}
