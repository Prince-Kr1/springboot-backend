package com.backend.TrackMoney.controller;

import com.backend.TrackMoney.dto.UserDTO;
import com.backend.TrackMoney.entity.User;
import com.backend.TrackMoney.exception.TrackMoneyException;
import com.backend.TrackMoney.jwt.AuthenticationRequest;
import com.backend.TrackMoney.jwt.AuthenticationResponse;
import com.backend.TrackMoney.jwt.JwtHelper;
import com.backend.TrackMoney.jwt.MyUserDetailsService;
import com.backend.TrackMoney.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest request) throws TrackMoneyException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(request.getEmail());
        final String token = jwtHelper.generateToken(userDetails.getUsername());

        UserDTO user = userService.getUserByEmail(request.getEmail());

        UserDTO safeUser = new UserDTO();
        safeUser.setId(user.getId());
        safeUser.setUsername(user.getUsername());
        safeUser.setEmail(user.getEmail());
        // Do NOT set password

        return ResponseEntity.ok(new AuthenticationResponse(token, safeUser));

//        return ResponseEntity.ok(new AuthenticationResponse(token, userService.getUserByEmail(request.getEmail())));
    }
}
