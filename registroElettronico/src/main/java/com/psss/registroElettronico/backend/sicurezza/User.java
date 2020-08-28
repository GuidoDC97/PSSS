package com.psss.registroElettronico.backend.sicurezza;

import com.sun.istack.NotNull;
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
    @Column(name="username", unique=true)
    private String username;
    private String password;
    private Boolean enabled = true;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserAuthority> authorities = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public void addAuthority(String authority) {
        authorities.add(new UserAuthority(this, authority));
    }
}
