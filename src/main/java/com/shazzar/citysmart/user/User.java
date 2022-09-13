package com.shazzar.citysmart.user;

import com.shazzar.citysmart.facility.feedback.Review;
import com.shazzar.citysmart.facility.hotel.Hotel;
import com.shazzar.citysmart.user.role.UserRole;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String firstName;
    private String lastName;

    //    @Pattern()
    @Column(nullable = false, unique = true)
    private String email;

    //    @Pattern()
//    @Size(min = 7, max = 12)
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
     private UserRole role;
    private LocalDateTime joinDate;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Review> reviews;

    @ManyToMany(mappedBy = "likes", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Hotel> likedHotels;

    @Column(nullable = false)
    private Boolean isEnabled = true;

//TODO: Create a facility class holding a user's owned facility
    @OneToMany(mappedBy = "owner")
    @ToString.Exclude
    private Set<Hotel> hotels;

    public User(String firstName, String email, String password) {
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.joinDate = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return userId != null && Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
