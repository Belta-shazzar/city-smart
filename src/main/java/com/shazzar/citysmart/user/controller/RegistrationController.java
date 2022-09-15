package com.shazzar.citysmart.user.controller;

import com.shazzar.citysmart.PublicResponse;
import com.shazzar.citysmart.config.userauth.AppUserService;
import com.shazzar.citysmart.user.model.request.SignInRequest;
import com.shazzar.citysmart.user.model.request.SignUpRequest;
import com.shazzar.citysmart.user.model.response.UserResponse;
import com.shazzar.citysmart.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class RegistrationController {

    private final UserService service;

    @PostMapping("/sign-up")
    public ResponseEntity<PublicResponse> createUser(@RequestBody SignUpRequest request) {
        return new ResponseEntity<>(service.createUser(request), HttpStatus.CREATED);
    }

    @PostMapping("/authentication")
    public ResponseEntity<UserResponse> authenticateUser(@RequestBody SignInRequest userRequest,
                                                         HttpServletRequest servletRequest) {
        return new ResponseEntity<>(service.authenticateUser(userRequest, servletRequest), HttpStatus.OK);
    }
}
