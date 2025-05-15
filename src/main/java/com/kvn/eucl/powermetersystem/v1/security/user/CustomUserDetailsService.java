package com.kvn.eucl.powermetersystem.v1.security.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kvn.eucl.powermetersystem.v1.entities.users.User;
import com.kvn.eucl.powermetersystem.v1.repositories.users.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private UsersRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = usersRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email %s not found", username)));
    return new UserPrincipal(user);
  }

}
