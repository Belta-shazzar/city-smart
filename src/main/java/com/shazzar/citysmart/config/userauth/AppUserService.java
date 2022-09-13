package com.shazzar.citysmart.config.userauth;

import com.shazzar.citysmart.user.User;
import com.shazzar.citysmart.user.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {

    private final UserRepo userRepo;

    public AppUserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format("user with %s %s not found", "username", email)));
        return new AppUser(user);
    }

}
