package com.remo.restapi.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.remo.restapi.models.AppRole;
import com.remo.restapi.models.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
public class SecurityService implements ISecurityService {
    @Value("${auth.token.exp_milliseconds}")
    private int auth_token_exp_time;

    @Value("${auth.refresh_token.exp_milliseconds}")
    private int auth_refresh_token_exp_time;

    @Autowired
    private IAppUserService userService;

    private Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

    private List<String> getAuthoritiesFromUser(User user) {
        return user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    @Override
    public String generateJWTAuthToken(String uri, User user) {
        String username = user.getUsername();
        List<String> authorities = getAuthoritiesFromUser(user);
        return generateJWTToken(uri, username, authorities, auth_token_exp_time);
    }

    @Override
    public String generateJWTAuthToken(String uri, String username) {
        AppUser user = userService.findByUsername(username);
        List<String> roles = user.getRoles().stream().map(AppRole::getName).collect(Collectors.toList());
        return this.generateJWTToken(uri, user.getUsername(), roles, this.auth_token_exp_time);
    }

    @Override
    public String generateJWTRefreshToken(String uri, User user) {
        String username = user.getUsername();
        List<String> authorities = getAuthoritiesFromUser(user);
        return generateJWTToken(uri, username, authorities, auth_refresh_token_exp_time);
    }

    @Override
    public String generateJWTToken(String uri, String username, List<String> authorities, int token_exp_time) {
        String token = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + token_exp_time))
                .withIssuer(uri)
                .withClaim("roles", authorities)
                .sign(algorithm);
        return token;
    }

    @Override
    public String verifyRefreshAndGenerateAuthToken(String uri, String authorizationHeader){
        DecodedJWT decodedJWT = verifyToken(authorizationHeader);
        String username = decodedJWT.getSubject();
        String access_token = this.generateJWTAuthToken(uri,username);
        return access_token;
    }

    @Override
    public DecodedJWT verifyToken(String authorizationHeader){
        String refresh_token = authorizationHeader.substring("Bearer ".length());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(refresh_token);
        return  decodedJWT;
    }

    @Override
    public UsernamePasswordAuthenticationToken buildAuthenticationToken(DecodedJWT decodedJWT) {
        String username = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role));
        });
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, null, authorities);
        return authenticationToken;
    }
}
