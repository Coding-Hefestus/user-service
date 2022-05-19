package com.uns.ac.rs.userservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private String jwt;
}
