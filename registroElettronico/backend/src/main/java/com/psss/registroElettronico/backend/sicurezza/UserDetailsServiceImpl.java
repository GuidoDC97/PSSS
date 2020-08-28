package com.psss.registroElettronico.backend.sicurezza;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utente utente = utenteRepository.findByUsername(username);
        if(utente == null){
            throw new UsernameNotFoundException("Username " + username + "non trovato");
        }
        return new User(utente.getUsername(), utente.getPassword(), getGrantedAuthority(utente));
    }

    private Collection<GrantedAuthority> getGrantedAuthority(Utente utente) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if(utente.getRuolo().getName().equalsIgnoreCase("segreteria")) {
            authorities.add(new SimpleGrantedAuthority("RUOLO_SEGRETERIA"));
        }
        return authorities;
    }
}
