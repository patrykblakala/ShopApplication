package com.patryk.shop.service.impl;

import com.patryk.shop.domain.dao.User;
import com.patryk.shop.repository.RoleRepository;
import com.patryk.shop.repository.UserRepository;
import com.patryk.shop.service.MailService;
import com.patryk.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Override
    public User register(User user) {
        var optionalRole = roleRepository.findByName("ROLE_USER");
        optionalRole.ifPresent(role -> user.setRoles(Collections.singleton(role)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        Map<String, Object> variables = new HashMap<>();
        variables.put("user", user);
        variables.put("url", "http://localhost:5000/api/templates/4");
        mailService.send(user.getEmail(), "User Registration confirmation", variables, null, null);
        return user;

    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public Page<User> page(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getCurrentUser() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("notFound.user"));
    }

    @Override
    @Transactional
    public User update(User user, Long id) {
        var userDb = getById(id);
        userDb.setEmail(user.getEmail());
        userDb.setPassword(user.getPassword());
        userDb.setRoles(user.getRoles());
        return userDb;
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
