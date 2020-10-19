package com.example.serviceuser.service;

import com.example.serviceuser.entity.User;
import com.example.serviceuser.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
public class UserService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> getUser(UUID uuid) {
        return userRepository.findById(uuid)
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("New Id: " + uuid);
                    return userRepository.save(new User(uuid));
                }))
                .cast(User.class);

    }

}