package com.pazarfy.ws.auth;

import com.pazarfy.ws.dto.UserVM;

import lombok.Data;

@Data
public class AuthResponse {

    private String token;

    private UserVM user;

}
