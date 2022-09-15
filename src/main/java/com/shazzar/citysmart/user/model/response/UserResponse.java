package com.shazzar.citysmart.user.model.response;

import com.shazzar.citysmart.user.role.UserRole;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private LocalDateTime joinDate;
    private String jwt;
}
