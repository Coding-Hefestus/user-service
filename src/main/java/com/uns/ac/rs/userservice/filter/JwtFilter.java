package com.uns.ac.rs.userservice.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uns.ac.rs.userservice.jwt.JwtValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import javax.servlet.Filter;
/**
 * @author Miljan Puletic
 */
//@Component
public class JwtFilter implements Filter {

    // @Autowired
    private JwtValidator jwtValidator;

    public JwtFilter(JwtValidator jwtValidator){
        super();
        this.jwtValidator = jwtValidator;
    }

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("Missing or invalid Authorization header");
        }

        final String token = authHeader.substring(7);

        try {

            if (!jwtValidator.isValid(token)){
                throw new ServletException("Invalid token");
            }
        } catch (final SignatureException e) {
            throw new ServletException("Invalid token");
        }

        chain.doFilter(req, res);
    }
}
