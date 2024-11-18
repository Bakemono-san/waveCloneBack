package org.odc.demo.Web.Validators;

import org.odc.demo.Datas.Entity.UserEntity;
import org.odc.demo.Datas.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApprenantValidator {

    @Autowired
    private UserRepository userRepository;

    private void validateUser(UserEntity user) {
        if (user == null) {
            throw new IllegalArgumentException("Les informations de l'utilisateur sont requises.");
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("L'email de l'utilisateur est requis.");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Un utilisateur avec cet email existe déjà.");
        }

        // Vous pouvez ajouter d'autres validations pour l'utilisateur ici (par exemple, format de l'email, mot de passe, etc.)
    }
}
