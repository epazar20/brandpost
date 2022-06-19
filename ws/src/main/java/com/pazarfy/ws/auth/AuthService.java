package com.pazarfy.ws.auth;

import java.net.PasswordAuthentication;
import java.nio.charset.Charset;
import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pazarfy.ws.configuration.AppConfig;
import com.pazarfy.ws.dto.UserVM;
import com.pazarfy.ws.user.IUserRepository;
import com.pazarfy.ws.user.UserService;
import com.pazarfy.ws.user.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthService {

    private IUserRepository userRepository;

    private PasswordEncoder encoder;

    private TokenRepository tokenRepository;

    private AppConfig config;

    private JwtUtil util;

    private AuthenticationManager authenticationManager;

    public AuthService(IUserRepository userRepository, PasswordEncoder encoder,TokenRepository tokenRepository,AppConfig config,JwtUtil util,AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.tokenRepository= tokenRepository;
        this.config=config;
        this.util=util;
        this.authenticationManager= authenticationManager;
    }

    public AuthResponse authenticate(AuthCrediential cred) throws AuthenticationException, Exception {

        // try {
        //     authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(cred.getUsername(), cred.getPassword()));
        // } catch (BadCredentialsException ex) {
        //     throw new Exception("Incorret username or password", ex);
        // }
        
        Users inDB = userRepository.findByUsername(cred.getUsername());
        if (inDB == null) {
            throw new AuthException();
        }
        if (encoder.matches(cred.getPassword(), inDB.getPassword())) {
            AuthResponse authResponse = new AuthResponse();
            UserVM userVM = new UserVM(inDB);
            String secret = config.getJwtSecretKey();
            System.out.println("Create: " + secret);
            String token = util.generateToken(inDB);
            // String token = Jwts.builder().setSubject(inDB.getId().toString())
            //         .signWith(SignatureAlgorithm.HS512, "secret-prod")
            //         .setExpiration(new Date(System.currentTimeMillis() + (1 * 60 * 60 * 1000)))
            //         .compact();

            //OPAQ TOKEN
            //String  token =generateTokenId();
            Token tok = new Token();
            tok.setToken(token);
            tok.setUser(inDB);
            tokenRepository.save(tok);

            authResponse.setToken(token);
            authResponse.setUser(userVM);
            return authResponse;
        } else {
            throw new AuthException();

        }
    }

    private String generateTokenId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Transactional
    public UserDetails getUserDetails(String token) {

        Optional<Token> opt = tokenRepository.findById(token);
        if(opt.isPresent())
        {
            Users user= opt.get().getUser();
            if(util.validateToken(token, user))
            {
                return user;
            }
            else{
                throw new AuthException();
            }
        }
        // String secret = config.getJwtSecretKey();//.getBytes(Charset.forName("UTF-8"));
        // System.out.println("Parser: " + secret);
        // JwtParser parser = Jwts.parser().setSigningKey("secret-prod");
        // try {
        //     parser.parse(token);
        //     Claims claims = parser.parseClaimsJws(token).getBody();
        //     String userStr = claims.getSubject();
        //     System.out.println(userStr);
        //     Long id = Long.parseLong(userStr);
        //     Optional<Users> userOpt = userRepository.findById(id);
        //     if (userOpt.isPresent()) {
        //         return userOpt.get();
        //     }

        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

        return null;
    }

}
