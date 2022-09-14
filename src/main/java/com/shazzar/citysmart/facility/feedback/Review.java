package com.shazzar.citysmart.facility.feedback;

import com.shazzar.citysmart.facility.extension.AuditModel;
import com.shazzar.citysmart.facility.hotel.Hotel;
import com.shazzar.citysmart.user.User;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
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

    private double customerRating;

    private String reviews;

    @ManyToOne(cascade = CascadeType.MERGE)
    private User users;

    @ManyToOne(cascade = CascadeType.MERGE)
    @ToString.Exclude
    private Hotel hotels;

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

