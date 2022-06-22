package com.agosh.userinfo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@Entity
@Table(name = "users")
@Data
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @NotNull(message = "First Name cannot be null")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotNull(message = "Last Name cannot be null")
    private String lastName;

    @Past(message = "Date of birth should be in the past")
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "Date of birth cannot be null")
    private Date dateOfBirth;


    @Column(nullable = false, unique = true, length = 45)
    @Email(message="Email should be valid format")
    @NotNull(message = "Email cannot be null")
    private String email;

}