package com.psss.registro.app.security;

import com.psss.registro.backend.models.AbstractEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity(name = "users") @Inheritance(strategy = InheritanceType.JOINED)
@ToString(exclude = {"userAuthority"}) @EqualsAndHashCode(exclude = {"userAuthority"})
public class User extends AbstractEntity {

    //@Column(unique=true)
    //@NotNull
    @Email(message = "Inserire una e-mail valida")
    @NotBlank(message = "Inserire una e-mail")
    @Size(min = 1, max = 50, message = "La e-mail deve essere compresa fra 1 e 50 caratteri")
    private String username;
    @NotBlank(message = "Inserire la password")
//    @Size(min = 1, max = 50, message = "La password deve essere compresa fra 1 e 50 caratteri")
    private String password;
    private Boolean enabled = true;
    @ManyToOne
    private UserAuthority userAuthority;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(User user) {
        this.username = user.username;
        this.password = user.password;
        this.enabled = user.enabled;
        this.userAuthority = user.userAuthority;
    }
}
