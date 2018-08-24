/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jdevy.domandj.user;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import java.util.Arrays;

/**
 *
 * @author devy
 *
 */
@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/{username}")
    public Mono<User> get(@PathVariable() String username) {
        return this.users.findByUsername(username);
    }

    @PostMapping("")
    public Mono<User> create(@RequestBody User user) {
        System.out.println("****** create user : " + user.getUsername() + " : " + user.getPassword() + " : " + user.getEmail());
        user.setRoles(Arrays.asList("ROLE_USER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        // User user = User.builder()
        //                     .roles(Arrays.asList("ROLE_USER"))
        //                     .username(user.getUsername())
        //                     .password(passwordEncoder.encode("password"))
        //                     .email(username + "@example.com")
        //                     .build();
        return this.users.save(user);
    }

}
