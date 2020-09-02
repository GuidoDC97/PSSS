package com.psss.registro.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(unique=true)
    private String username;
    private String password;
    private Boolean enabled = true;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserAuthority> userAuthorities = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(User user) {
        this.id = user.id;
        this.username = user.username;
        this.password = user.password;
        this.enabled = user.enabled;
        userAuthorities = user.userAuthorities;
    }

    public void addAuthority(String authority) {
        userAuthorities.add(new UserAuthority(this, authority));
    }
}
