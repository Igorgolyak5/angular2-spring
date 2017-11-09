package com.loopme.service;

import com.loopme.model.User;
import com.loopme.model.UserRole;
import com.loopme.repository.UserRepository;
import com.loopme.exceptions.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Igor Holiak.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(final User user) {
        if (user == null) {
            throw new IllegalArgumentException("user must not be null");
        }
        userRepository.saveOrUpdate(user);
    }

    @Transactional
    public void update(final User updated) {
        User oldUser = findOne(updated.getId());
        if (oldUser == null) {
            throw new IllegalArgumentException("User not found");
        }
        String newEmail = updated.getEmail();
        if (!oldUser.getEmail().equalsIgnoreCase(newEmail)) {
            if (findOneByEmail(newEmail).isPresent()) {
                throw new BaseException("User with such email already exists!");
            }
        }
        oldUser.setPassword(passwordEncoder.encode(updated.getPassword()));
        oldUser.setEmail(updated.getEmail());
        oldUser.setName(updated.getName());
        userRepository.saveOrUpdate(oldUser);
    }

    public void delete(final Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id shouldn't be null");
        }
        userRepository.delete(id);
    }

    public User findOne(final Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id shouldn't be null");
        }
        return userRepository.findOne(id);
    }

    public Optional<User> findOneByEmail(final String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email shouldn't be null");
        }
        return Optional.ofNullable(userRepository.findOneByEmail(email));
    }

    public List<User> findByRole(final UserRole role) {
        if (role == null) {
            throw new IllegalArgumentException("Role shouldn't be null");
        }
        return userRepository.findByRole(role);
    }

    public List<User> findFreePublisher() {
        return userRepository.findFreePublisher();
    }
}
