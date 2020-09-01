package com.psss.registro.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "authorities")
public class UserAuthority {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @ManyToOne
    User user;
    String authority;

    public UserAuthority(User user, String authority) {
        this.user = user;
        this.authority = authority;
    }
}
