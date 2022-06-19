package com.pazarfy.ws.auth;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.pazarfy.ws.user.Users;

import lombok.Data;

@Entity
@Data
public class Token {

    @Id
    String token;

    @ManyToOne
    Users user;
    
}
