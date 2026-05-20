package com.soutenance.security;

import com.soutenance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUser securityUser = userRepository.findByEmailIgnoreCase(username)
            .map(SecurityUser::new)
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable"));
        if (!securityUser.isEnabled()) {
            throw new DisabledException("Compte inactif");
        }
        return securityUser;
    }
}
