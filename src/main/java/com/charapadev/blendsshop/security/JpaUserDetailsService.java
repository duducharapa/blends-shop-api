package com.charapadev.blendsshop.security;

import com.charapadev.blendsshop.modules.users.User;
import com.charapadev.blendsshop.modules.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByAccessCode(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        return new CustomUserDetails(user);
    }

}
