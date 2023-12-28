package ru.simbirgo.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="username", nullable = false, unique = true)
    private String username;
    @Column(name="password", nullable = false)
    private String password;
    @Column(name="gender")
    private String gender;
    @Column(name="is_admin", nullable=false)
    private Boolean isAdmin;


    public User(String username, String password,String gender, Boolean isAdmin){
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.isAdmin =isAdmin;
    }

    public User(String username, String password, Boolean isAdmin){
        this.username = username;
        this.isAdmin = isAdmin;
        this.password = password;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

}
