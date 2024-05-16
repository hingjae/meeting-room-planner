package com.honey.meetingroomplanner.security.service;

import com.honey.meetingroomplanner.security.dto.CustomUserDetails;
import com.honey.meetingroomplanner.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username)
                .map(CustomUserDetails::from)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }
}
