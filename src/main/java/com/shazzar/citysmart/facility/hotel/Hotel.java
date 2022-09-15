package com.shazzar.citysmart.facility.hotel;

import com.shazzar.citysmart.facility.extension.AuditModel;
import com.shazzar.citysmart.facility.extension.Images;
import com.shazzar.citysmart.facility.extension.Location;
import com.shazzar.citysmart.facility.feedback.Review;
import com.shazzar.citysmart.user.User;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@Table(name = "hotel")
@NoArgsConstructor
public class Hotel extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelId;
    @Column(nullable = false, unique = true)
    private String hotelName;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ownerId",
            referencedColumnName = "userId",
            nullable = false)
    private User owner;

    @Embedded
    private Images images;

    @Embedded
    private Location location;

    private BigDecimal pricePerNight;

    @ManyToMany
    @JoinTable(name="customer_likes",
            joinColumns=
            @JoinColumn(name="hotel_id", referencedColumnName="hotelId"),
            inverseJoinColumns=
            @JoinColumn(name="user_id", referencedColumnName="userId")
    )
    @ToString.Exclude
    private Set<User> likes;
//    set an int field for likes

    //    Customer's rate | Total rates | 5
    private double ratings;

    @OneToMany
    @ToString.Exclude
    private List<Review> review;


    public Hotel(String hotelName, User owner) {
        this.hotelName = hotelName;
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Hotel hotel = (Hotel) o;
        return hotelId != null && Objects.equals(hotelId, hotel.hotelId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
