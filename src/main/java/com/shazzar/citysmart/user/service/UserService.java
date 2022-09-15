package com.shazzar.citysmart.user.service;

import com.shazzar.citysmart.PublicResponse;
import com.shazzar.citysmart.user.model.request.SignInRequest;
import com.shazzar.citysmart.user.model.request.SignUpRequest;
import com.shazzar.citysmart.user.model.response.UserResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    PublicResponse createUser(SignUpRequest request);
    UserResponse authenticateUser(SignInRequest userRequest, HttpServletRequest servletRequest);

    List<UserResponse> getAllUser();

}
