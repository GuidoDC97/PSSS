package com.psss.registro.security;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity(name = "users") @Inheritance(strategy = InheritanceType.JOINED)
@ToString(exclude = {"userAuthority"}) @EqualsAndHashCode(exclude = {"userAuthority"})
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(unique=true)
    private String username;
    private String password;
    private Boolean enabled = true;
    @ManyToOne
    private UserAuthority userAuthority;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(User user) {
        this.id = user.id;
        this.username = user.username;
        this.password = user.password;
        this.enabled = user.enabled;
        this.userAuthority = user.userAuthority;
    }
}
