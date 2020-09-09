package com.psss.registro.app.security;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity(name = "authorities") @ToString(exclude = {"users"}) @EqualsAndHashCode(exclude = {"users"})
public class UserAuthority {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    String authority;
    @OneToMany(mappedBy = "userAuthority", fetch = FetchType.EAGER)
    List<User> users;

    public UserAuthority(String authority) {
        users = new ArrayList<>();
        this.authority = authority;
    }

    public void addUser(User user) {
        users.add(user);
    }
}
