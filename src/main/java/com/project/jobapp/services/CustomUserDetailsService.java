package com.project.jobapp.services;

import com.project.jobapp.entity.Users;
import com.project.jobapp.repository.UsersRepository;
import com.project.jobapp.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("could not found user"));
        return new CustomUserDetails(users);
    }
}
