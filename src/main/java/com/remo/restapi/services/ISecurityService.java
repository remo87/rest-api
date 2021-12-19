package com.remo.restapi.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ISecurityService {

    String generateJWTAuthToken(String uri, User user);
    String generateJWTAuthToken(String uri, String username);
    String generateJWTRefreshToken(String uri, User user);
    String generateJWTToken(String uri, String username, List<String> authorities, int token_exp_time);
    String verifyRefreshAndGenerateAuthToken(String uri, String authorizationHeader);
    DecodedJWT verifyToken(String authorizationHeader);
    UsernamePasswordAuthenticationToken buildAuthenticationToken(DecodedJWT decodedJWT);
}
