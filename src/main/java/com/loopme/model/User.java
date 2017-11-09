package com.loopme.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * @author Igor Holiak
 */
@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @NotEmpty(message = "name must not be empty")
    @Column(name = "name", nullable = false)
    private String name;

    @NotEmpty(message = "email must not be empty")
    @Email(message = "please enter a valid email")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotEmpty(message = "password must not be empty")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "app_id")
    private App app;
}
