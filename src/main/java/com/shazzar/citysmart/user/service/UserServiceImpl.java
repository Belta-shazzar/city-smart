package com.shazzar.citysmart.user.service;

import com.shazzar.citysmart.PublicResponse;
import com.shazzar.citysmart.config.JwtUtil;
import com.shazzar.citysmart.config.userauth.AppUserService;
import com.shazzar.citysmart.exception.AlreadyExistException;
import com.shazzar.citysmart.user.User;
import com.shazzar.citysmart.user.UserRepo;
import com.shazzar.citysmart.user.model.Mapper;
import com.shazzar.citysmart.user.model.request.SignInRequest;
import com.shazzar.citysmart.user.model.request.SignUpRequest;
import com.shazzar.citysmart.user.model.response.UserResponse;
import com.shazzar.citysmart.user.role.UserRole;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final AppUserService userService;
    private final UserRepo userRepo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(AppUserService userService, UserRepo userRepo,
                           JwtUtil jwtUtil, PasswordEncoder encoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public PublicResponse createUser(SignUpRequest request) {
        checkIfEmailExist(request.getEmail());
        String password = encoder.encode(request.getPassword());
        User user = new User(request.getFirstName(),
                request.getEmail(), password);
        user.setLastName(request.getLastName());
        user.setRole(UserRole.CUSTOMER);
        userRepo.save(user);
//        TODO: Email verification token feature
        return new PublicResponse(String.format("%s %s has been added", request.getFirstName(), request.getLastName()));
    }

    @SneakyThrows
    public void checkIfEmailExist(String userEmail) {
        Optional<User> user = userRepo.findByEmail(userEmail);
        if (user.isPresent()) {
            throw new AlreadyExistException(String.format("%s with %s %s, already exist",
                    "User", "email", userEmail));
        }
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
