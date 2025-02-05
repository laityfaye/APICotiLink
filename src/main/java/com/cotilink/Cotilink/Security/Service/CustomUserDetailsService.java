package com.cotilink.Cotilink.Security.Service;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cotilink.Cotilink.Model.Membre;
import com.cotilink.Cotilink.Repository.MembreRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MembreRepository membreRepository;

    // Injection de dépendance via constructeur (meilleure pratique)
    public CustomUserDetailsService(MembreRepository membreRepository) {
        this.membreRepository = membreRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String telephone) throws UsernameNotFoundException {
        Membre user = membreRepository.findByTelephone(telephone);

        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé : " + telephone);
        }

        return new User(user.getTelephone(), user.getPassword(), getGrantedAuthorities(user.getRole()));
    }

    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        // Vérifier si "ROLE_" est déjà présent pour éviter de le doubler
        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}
