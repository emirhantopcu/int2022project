package com.example.int2022.service;

import com.example.int2022.model.ApplicationUser;
import com.example.int2022.repository.ApplicationUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final ApplicationUserRepository applicationUserRepository;

    public JwtUserDetailsService(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = applicationUserRepository.findApplicationUserByName(username);

        if (user == null){
            throw new UsernameNotFoundException("User Not Found with -> username : " + username);
        }

        return new User(user.getName(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                user.getUserRole().getGrantedAuthorities());
    }
}
