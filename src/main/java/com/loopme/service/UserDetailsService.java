package com.loopme.service;

import com.loopme.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final User user = userService.findOneByEmail(email).orElseThrow(() ->
            new UsernameNotFoundException(String.format("username %s not found", email)));

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(), user.getPassword(),
            AuthorityUtils.createAuthorityList(user.getRole().toString()));
    }
}
