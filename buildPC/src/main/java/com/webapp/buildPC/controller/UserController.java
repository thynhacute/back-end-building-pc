package com.webapp.buildPC.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.webapp.buildPC.domain.Role;
import com.webapp.buildPC.domain.User;
import com.webapp.buildPC.service.interf.RoleService;
import com.webapp.buildPC.service.interf.UserService;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final RoleService roleService;

    @PostMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.findUserByID(username);
                Role role = roleService.findRoleName(user.getRoleID());
                String access_token = JWT.create()
                        .withSubject(user.getUserID())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",
                            Arrays.asList(role.getRoleName()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    @PostMapping ("/token/google")
    public void tokenGoogle(@RequestBody String token ,HttpServletRequest request, HttpServletResponse response) throws IOException {
            try {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseToken decodedToken = auth.verifyIdToken(token);
                String name = decodedToken.getEmail();
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                User user = userService.findUserByEmail(name);
                Role role;
                if(user == null){
                    String userID = decodedToken.getUid();
                    String userName = decodedToken.getName();
                    String image = decodedToken.getPicture();
                    String email = decodedToken.getEmail();
                    int status;
                    if(decodedToken.isEmailVerified() == true){
                        status = 1;
                    }else{
                        status = 0;
                    }
                    user = new User(userID, userName, "", email, "", "", image, 2, status);
                    userService.createUserByNotFound(user);
                    role = roleService.findRoleName(user.getRoleID());
                }else {
                    role = roleService.findRoleName(user.getRoleID());
                }
                String access_token = JWT.create()
                        .withSubject(user.getUserID())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",
                                Arrays.asList(role.getRoleName()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", token);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
    }


}
