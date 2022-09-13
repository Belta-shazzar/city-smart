package com.shazzar.citysmart;

import com.shazzar.citysmart.user.User;
import com.shazzar.citysmart.user.UserRepo;
import com.shazzar.citysmart.user.role.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CitySmartApplication implements CommandLineRunner {

    private final UserRepo repo;
    private final PasswordEncoder encoder;

    public CitySmartApplication(UserRepo repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(CitySmartApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User("Abel", "abel@gmail.com", "password");
        User user2 = new User("Chimdi", "chimdi@gmail.com", "password");
        User user3 = new User("Dubem", "dubem@gmail.com", "password");
        User user4 = new User("Chika", "chika@gmail.com", "password");
        User user5 = new User("Langstone", "lang@gmail.com", "password");

        user1.setPassword(encoder.encode(user1.getPassword()));
        user2.setPassword(encoder.encode(user2.getPassword()));
        user3.setPassword(encoder.encode(user3.getPassword()));
        user4.setPassword(encoder.encode(user4.getPassword()));
        user5.setPassword(encoder.encode(user5.getPassword()));

        user1.setRole(UserRole.CUSTOMER);
        user2.setRole(UserRole.CUSTOMER);
        user3.setRole(UserRole.CUSTOMER);
        user4.setRole(UserRole.CUSTOMER);
        user5.setRole(UserRole.CUSTOMER);

        user1.setLastName("Zubby");
        user2.setLastName("Daalu");
        user3.setLastName("Emenike");
        user4.setLastName("Tobechukwu");
        user5.setLastName("Zekke");

//        repo.save(user1);
//        repo.save(user2);
//        repo.save(user3);
//        repo.save(user4);
//        repo.save(user5);


    }

    /*
    TODO:
    - create jwt features
    - email verification
    - user sign in / sign up
    - create hotel
    - get hotels for home page (pagination and sort)
    - get hotels for hotels page
    */

}
