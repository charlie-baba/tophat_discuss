package com.tophat.discuss.data.models;

import com.tophat.discuss.data.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Charles on 17/12/2022
 */
@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "user_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

}
