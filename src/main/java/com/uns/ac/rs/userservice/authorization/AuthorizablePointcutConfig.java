package com.uns.ac.rs.userservice.authorization;

import org.aspectj.lang.annotation.Pointcut;

public class AuthorizablePointcutConfig {

    @Pointcut("@annotation(com.uns.ac.rs.userservice.authorization.Authorizable)")
    public void authorizable() {}
}
