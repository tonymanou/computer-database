package com.tonymanou.computerdb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tonymanou.computerdb.dao.IUserDAO;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  private IUserDAO userDAO;

  @Transactional(readOnly = true)
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    com.tonymanou.computerdb.model.User user = userDAO.findByName(username);

    if (user == null) {
      throw new UsernameNotFoundException(username);
    }

    return new User(
        user.getName(),
        user.getPass(),
        true, // Account enabled
        true, // Account not expired
        true, // Credentials not expired
        true, // Account not locked
        getAuthorities(user));
  }

  private List<GrantedAuthority> getAuthorities(com.tonymanou.computerdb.model.User user) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    Byte role = user.getRole();

    if (role != null) {
      if (role == 0) {
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
      } else if (role == 1) {
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
      }
    }

    return authorities;
  }
}
