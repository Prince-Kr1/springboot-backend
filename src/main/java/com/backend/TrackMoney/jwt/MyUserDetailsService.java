package com.backend.TrackMoney.jwt;

import com.backend.TrackMoney.dto.UserDTO;
import com.backend.TrackMoney.exception.TrackMoneyException;
import com.backend.TrackMoney.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            UserDTO userDTO = userService.getUserByEmail(email);
            return new MyUserDetails(userDTO.getId(), email, userDTO.getPassword(), new ArrayList<>());
        } catch (TrackMoneyException e) {
            throw new RuntimeException(e);
        }
    }
}
