package com.adrign93.tokenGenerator.service;

import com.adrign93.tokenGenerator.domain.dto.TokenRequest;
import com.adrign93.tokenGenerator.domain.entity.User;
import com.adrign93.tokenGenerator.exception.ResourceNotFoundException;
import com.adrign93.tokenGenerator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import com.adrign93.tokenGenerator.transform.TokenToUser;

import java.util.Optional;

/**
 * Implementación de la interfaz UserService
 */
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Comprueba si existe un usuario con el username ingresado
     * @param username String
     * @return User
     */
    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el usuario"));
    }

    /**
     * Comprueba que el usuario ingresado sea el correcto
     * @param tokenRequest TokenRequest
     */
    @Override
    public void validateUser(TokenRequest tokenRequest){
        User user = TokenToUser.mapTokenToUser(tokenRequest);
        User userFound = findByUsername(user.getUsername());
        if(!userFound.getPassword().equals(user.getPassword())
            || !userFound.getEntity().equals(user.getEntity())){
            throw new IllegalArgumentException("Usuario / contraseña incorrectos");
        }
    }
}
