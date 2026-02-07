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
        User userRequest = TokenToUser.mapTokenToUser(tokenRequest);
        User userBBDD = findByUsername(userRequest.getUsername());
        validatePassword(userBBDD.getPassword(), userRequest.getPassword());
        validateEntity(userBBDD.getEntity(), userRequest.getEntity());
    }

    /**
     * Comprueba si la contraseña que tenemos en la base de datos es la misma que nos ha llegado
     * en la petición
     * @param passwordBBDD String
     * @param password String
     */
    private void validatePassword(String passwordBBDD, String password) {
        if(!passwordBBDD.equals(password)){
            throw new IllegalArgumentException("Usuario / contraseña incorrectos");
        }
    }

    /**
     * Comprueba si la entidad que hay enla base de datos es la misma que nos ha llegado en la petición
     * @param entityBBDD String
     * @param entity String
     */
    private void validateEntity(String entityBBDD, String entity) {
        if(!entityBBDD.equals(entity)){
            throw new IllegalArgumentException("Usuario / contraseña incorrectos");
        }
    }
}
