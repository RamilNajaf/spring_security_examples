package com.jwt.security;

import com.jwt.entity.User;
import com.jwt.repostiroy.UserRepository;
import com.jwt.utils.Constants;
import com.jwt.utils.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private UserRepository userRepository;

    private final TokenManager tokenManager;


    public JwtFilter(TokenManager tokenManager) {
        this.tokenManager  = tokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader(Constants.HEADER_KEY);

        String username = null;
        String token = null;


        if (authHeader != null && authHeader.contains(Constants.TOKEN_PREFIX)) {
            token = authHeader.substring(7);
            try {
                username = tokenManager.getUsernameFromToken(token);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        if (username != null && token != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (tokenManager.tokenValidate(token)) {

                User user= userRepository.findByUsername(username);
                List<GrantedAuthority> authorityList = new ArrayList<>();
                user.getRoleList().forEach(r -> {
                    GrantedAuthority  authority = new SimpleGrantedAuthority("ROLE_"+r);
                    authorityList.add(authority);
                });


                UsernamePasswordAuthenticationToken upassToken =
                        new UsernamePasswordAuthenticationToken(username, null, authorityList);
                upassToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(upassToken);
            }
        }

        filterChain.doFilter(request, response);

    }


}
