package com.shazzar.citysmart.user.service;

import com.shazzar.citysmart.user.model.request.SignInRequest;
import com.shazzar.citysmart.user.model.response.UserResponse;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    UserResponse authenticateUser(SignInRequest userRequest, HttpServletRequest servletRequest);
}
