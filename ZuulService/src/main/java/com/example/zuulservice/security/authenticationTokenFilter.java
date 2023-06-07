package com.example.zuulservice.security;

import com.example.zuulservice.model.User;
import com.example.zuulservice.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.IOException;
import java.util.Optional;

@Component
public class authenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTUtil jwtUtil;
    private final String tokenHeader;

    public authenticationTokenFilter(UserService userService, JWTUtil jwtUtil,@Value("Authorization") String tokenHeader) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.tokenHeader = tokenHeader;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            final String requestHeader = request.getHeader("Authorization");

            String username = null;
            String authToken = null;
            User user;
            if (requestHeader != null && requestHeader.startsWith("Bearer ")&&jwtUtil.validateAccessToken(requestHeader.substring(7))) {
                authToken = requestHeader.substring(7);
                try {
                    username = jwtUtil.getUsernameFromToken(authToken);
                } catch (IllegalArgumentException e) {
//                    logger.error("an error occured during getting username from token", e);
                    throw e;
                } catch (ExpiredJwtException e) {
//                    logger.warn("the token is expired and not valid anymore", e);
                    throw e;
                }
            } else {
                logger.warn("couldn't find bearer string, will ignore the header");
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                logger.debug("security context was null, so authorizating user");

                Optional<User> optionalUser = this.userService.getUser(username);
                if(!optionalUser.isPresent()) {
                    throw new Exception();
                }
                user = optionalUser.get();

                if (jwtUtil.validateAccessToken(authToken)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, null);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            return;
        }
        chain.doFilter(request, response);
    }
}
