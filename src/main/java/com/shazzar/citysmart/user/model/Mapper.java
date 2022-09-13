package com.shazzar.citysmart.user.model;

import com.shazzar.citysmart.user.User;
import com.shazzar.citysmart.user.model.response.UserResponse;

public class Mapper {
    public static UserResponse userToUserModel(User user, String jwt) {
        UserResponse response = new UserResponse();
        response.setUserId(user.getUserId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setJoinDate(user.getJoinDate());
        response.setJwt(jwt);
        return response;
    }
}
