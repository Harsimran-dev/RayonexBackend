package com.healthbackend.services.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.healthbackend.entities.PensionUser;
import com.healthbackend.repositories.PensionUserRepository;
import com.healthbackend.repositories.PensionUserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final PensionUserRepository pensionuserRepository;

    public UserServiceImpl(PensionUserRepository pensionuserRepository) {
        this.pensionuserRepository = pensionuserRepository;
    }

    @Override
    public UserDetailsService UserDetailsService() {

        return new UserDetailsService() {

            @Override

            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

                return pensionuserRepository.findByEmail(username)

                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }

        };
    }

}
