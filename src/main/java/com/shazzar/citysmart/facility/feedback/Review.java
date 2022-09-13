package com.shazzar.citysmart.facility.feedback;

import com.shazzar.citysmart.facility.extension.AuditModel;
import com.shazzar.citysmart.facility.hotel.Hotel;
import com.shazzar.citysmart.user.User;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Review extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private String reviews;

    @ManyToMany
    @JoinTable(name = "user_review",
            joinColumns =
            @JoinColumn(name = "review_id", referencedColumnName = "reviewId"),
            inverseJoinColumns =
            @JoinColumn(name = "user_id", referencedColumnName = "userId")
    )
    @ToString.Exclude
    private List<User> users;

    @ManyToMany(mappedBy = "review", cascade = CascadeType.MERGE)
    @ToString.Exclude
    private List<Hotel> hotels;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Review review = (Review) o;
        return reviewId != null && Objects.equals(reviewId, review.reviewId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

