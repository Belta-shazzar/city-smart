package com.shazzar.citysmart.user.service;

import com.shazzar.citysmart.config.JwtUtil;
import com.shazzar.citysmart.config.userauth.AppUserService;
import com.shazzar.citysmart.user.User;
import com.shazzar.citysmart.user.UserRepo;
import com.shazzar.citysmart.user.model.Mapper;
import com.shazzar.citysmart.user.model.request.SignInRequest;
import com.shazzar.citysmart.user.model.response.UserResponse;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final AppUserService userService;
    private final UserRepo userRepo;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(AppUserService userService, UserRepo userRepo, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public UserResponse authenticateUser(SignInRequest userRequest, HttpServletRequest servletRequest) {
        String username = userRequest.getEmail();
        String password = userRequest.getPassword();
        authenticate(username, password);

        UserDetails userDetails = userService.loadUserByUsername(username);
        String jwt = jwtUtil.generateToken(userDetails, servletRequest);
        User user = userRepo.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("user with %s %s not found", "username", username)));

        return Mapper.userToUserModel(user, jwt);
    }

    @Override
    public List<UserResponse> getAllUser() {
        List<User> allUser = userRepo.findAll();
        List<UserResponse> responses = new ArrayList<>();

        for (User user : allUser) {
            UserResponse response = new UserResponse();
            response.setUserId(user.getUserId());
            response.setFirstName(user.getFirstName());
            response.setLastName(user.getLastName());
            response.setEmail(user.getEmail());
            response.setRole(user.getRole().name());
            responses.add(response);
        }
        return responses;
    }

    @SneakyThrows
    public void authenticate(String username, String password) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException ex) {
            throw new Exception("USER_DISABLED", ex);
        } catch (BadCredentialsException ex) {
            throw new Exception("INVALID_CREDENTIAL", ex);
        }
    }
}
