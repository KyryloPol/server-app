package com.trainme.treainmeapp.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.trainme.treainmeapp.domain.enums.EMembership;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

/**
 * It's a DTO class that contains all the fields that are needed to create a new user
 */
@Data
public class UserDTO {
    private Long id;
    private String username;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirthday;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate membershipFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate membershipTo;
    private Set<EMembership> memberships;
}

