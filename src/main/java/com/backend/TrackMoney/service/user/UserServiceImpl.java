package com.backend.TrackMoney.service.user;

import com.backend.TrackMoney.dto.LoginDTO;
import com.backend.TrackMoney.dto.SignupResponseDTO;
import com.backend.TrackMoney.dto.UserDTO;
import com.backend.TrackMoney.entity.User;
import com.backend.TrackMoney.exception.TrackMoneyException;
import com.backend.TrackMoney.jwt.JwtHelper;
import com.backend.TrackMoney.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "userService")
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final JwtHelper jwtHelper;
    private final HttpServletRequest request;

    @Override
    public SignupResponseDTO registerUser(UserDTO userDTO) throws TrackMoneyException {
        Optional<User> optional = userRepository.findByEmail(userDTO.getEmail());
        if (optional.isPresent()) throw new TrackMoneyException("USER_FOUND");

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userDTO.toEntity();

        user = userRepository.save(user);

        // Now use email to generate token (after save so it's persisted)
        String token = jwtHelper.generateToken(user.getEmail());

        return new SignupResponseDTO(token, user.toDTO());
    }

    @Override
    public UserDTO loginUser(LoginDTO loginDTO) throws TrackMoneyException {
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new TrackMoneyException("USER_NOT_FOUND"));
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword()))
            throw new TrackMoneyException("INVALID_CREDENTIALS");
        return user.toDTO();
    }

    @Override
    public UserDTO getUserByEmail(String email) throws TrackMoneyException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TrackMoneyException("USER_NOT_FOUND"));
        return user.toDTO();
    }

    @Override
    public User getCurrentUser() {
        // 1. Extract the Authorization header
        String authHeader = request.getHeader("Authorization");

        // 2. Validate and extract token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7); // remove "Bearer "
        String email = jwtHelper.getUsernameByToken(token);

        // 3. Get user from database
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
    }





