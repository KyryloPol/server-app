package com.trainme.treainmeapp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trainme.treainmeapp.domain.enums.EMembership;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.*;
import java.util.*;

/**
 * User is a JPA class that represents entity in database and has a one-to-many relationship with Training entity
 * and ERoles membership class
 */
@Data
@Entity
public class User implements UserDetails {
    // It's a JPA annotation that tells the JPA provider to generate the id for the entity.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // It's a JPA annotation that tells the JPA provider to create a unique constraint on the email column.
    @Column(unique = true)
    private String email;

    // It's a JPA annotation that tells the JPA provider to create a unique constraint on the username column.
    @Column(unique = true)
    private String username;

    // It's a JPA annotation that tells the JPA provider to create a column with a length of 3000 characters and it's not
    // nullable.
    @Column(nullable = false, length = 3000)
    private String password;

    // It's a JPA annotation that tells the JPA provider to create a table called user_membership with two columns: user_id
    // and membership.
    @ElementCollection(targetClass = EMembership.class)
    @CollectionTable(name = "user_membership",
        joinColumns = @JoinColumn(name = "user_id"))
    private Set<EMembership> membership = new HashSet<>();

    // It's a Jackson annotation that tells the Jackson provider to format the dateOfBirthday field in the yyyy-mm-dd
    // format.
    @JsonFormat(pattern = "yyyy-mm-dd")
    private LocalDate dateOfBirthday;

    // It's a Jackson annotation that tells the Jackson provider to format the dateOfBirthday field in the yyyy-mm-dd
    //     format.
    @JsonFormat(pattern = "yyyy-mm-dd")
    private LocalDate membershipFrom;

    // It's a Jackson annotation that tells the Jackson provider to format the dateOfBirthday field in the yyyy-mm-dd
    //     format.
    @JsonFormat(pattern = "yyyy-mm-dd")
    private LocalDate membershipTo;

    // It's a JPA annotation that tells the JPA provider to create a one-to-many relationship between the User and
    // Training entities.
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Training> trainings = new ArrayList<>();

    // It's a JPA annotation that tells the JPA provider to ignore the authorities field when it's creating the database
    // schema.
    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    // It's a constructor that is used by the Spring Security framework to create a User object.
    public User(Long id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    // It's a default constructor that is used by the Spring Security framework to create a User object.
    public User(){}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
