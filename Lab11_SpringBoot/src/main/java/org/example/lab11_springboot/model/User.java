package org.example.lab11_springboot.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name ="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
