package com.psss.registroElettronico.backend.sicurezza;

import com.psss.registroElettronico.backend.model.Utente;
import com.psss.registroElettronico.backend.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UtenteService utenteService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Utente.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Utente utente = (Utente) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (utente.getUsername().length() < 6 || utente.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (utenteService.findByUsername(utente.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (utente.getPassword().length() < 8 || utente.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!utente.getPasswordConfirm().equals(utente.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
}

