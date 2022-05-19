package com.uns.ac.rs.userservice.authorization;

import com.uns.ac.rs.userservice.jwt.JwtValidator;
import com.uns.ac.rs.userservice.model.User;
import com.uns.ac.rs.userservice.repository.UserRepository;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@Aspect
public class AuthorizeAspect {

    private  JwtValidator jwtValidator;
    private  UserRepository userRepository;

    @Autowired
    public AuthorizeAspect( JwtValidator jwtValidator, UserRepository userRepository) {
        this.jwtValidator = jwtValidator;
        this.userRepository = userRepository;
    }

    @Around("com.uns.ac.rs.userservice.authorization.AuthorizablePointcutConfig.authorizable()")
    public Object aroundAuthorizableMethod(ProceedingJoinPoint pjp) throws Throwable {

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Authorizable annotation = method.getAnnotation(Authorizable.class);
        if (annotation.roles().length == 0) pjp.proceed();
        Object[] args = pjp.getArgs();
        ServerHttpRequest request  = (ServerHttpRequest) args[0];
        List<String> authorization = request.getHeaders().get("Authorization");
        if (CollectionUtils.isEmpty(authorization) || StringUtils.isBlank(authorization.get(0))) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        String jwtToken = request.getHeaders().get("Authorization").get(0);
        Integer userId = jwtValidator.extractUserId(jwtToken.substring(7));
        User u = userRepository.getById(userId);
        if (u == null || !Arrays.asList(annotation.roles()).contains(u.getRole())) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return pjp.proceed();

    }
}
