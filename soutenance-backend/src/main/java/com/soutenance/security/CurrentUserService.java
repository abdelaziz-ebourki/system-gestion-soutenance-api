package com.soutenance.security;

import com.soutenance.entity.Student;
import com.soutenance.entity.Teacher;
import com.soutenance.entity.User;
import com.soutenance.exception.UnauthorizedException;
import com.soutenance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    public User user() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new UnauthorizedException("Utilisateur non authentifie");
        }
        return userRepository.findByEmailIgnoreCase(authentication.getName())
            .orElseThrow(() -> new UnauthorizedException("Utilisateur non authentifie"));
    }

    public Teacher teacher() {
        User user = user();
        if (user instanceof Teacher teacher) {
            return teacher;
        }
        throw new UnauthorizedException("Acces enseignant requis");
    }

    public Student student() {
        User user = user();
        if (user instanceof Student student) {
            return student;
        }
        throw new UnauthorizedException("Acces etudiant requis");
    }
}
