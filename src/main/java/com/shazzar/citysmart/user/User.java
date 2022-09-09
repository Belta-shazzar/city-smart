package com.shazzar.citysmart.user;

import com.shazzar.citysmart.facility.feedback.Review;
import com.shazzar.citysmart.facility.hotel.Hotel;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Size;
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
    @Size(min = 7, max = 12)
    @Column(nullable = false)
    private String password;

    // private Role role;
    private LocalDateTime joinDate;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Review> reviews;

    @ManyToMany(mappedBy = "likes", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Hotel> likedHotels;

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
