package com.bharath.watchSpace.SecurityPackage;

import com.bharath.watchSpace.Entities.Users;
import com.bharath.watchSpace.ExceptionsAndErrors.ResourceNotFoundException;
import com.bharath.watchSpace.RepositoryPackage.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailService implements UserDetailsService
{
    @Autowired
    UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Users user = userRepository.findByusername(username).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));

        if(user == null)
        {
            throw new ResourceNotFoundException("User Not Found");
        }

        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
