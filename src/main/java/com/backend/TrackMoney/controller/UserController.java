package com.backend.TrackMoney.controller;

import com.backend.TrackMoney.dto.LoginDTO;
import com.backend.TrackMoney.dto.SignupResponseDTO;
import com.backend.TrackMoney.dto.UserDTO;
import com.backend.TrackMoney.entity.User;
import com.backend.TrackMoney.exception.TrackMoneyException;
import com.backend.TrackMoney.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@Validated
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signupUser(@RequestBody @Valid UserDTO userDTO) throws TrackMoneyException {
        SignupResponseDTO signupResponseDTO = userService.registerUser(userDTO);
        return new ResponseEntity<>(signupResponseDTO, HttpStatus.CREATED);
    }

//    @PostMapping("/login")
//    public ResponseEntity<UserDTO> loginUser(@RequestBody @Valid LoginDTO loginDTO) throws TrackMoneyException {
//        return new ResponseEntity<>(userService.loginUser(loginDTO), HttpStatus.OK);
//    }



}
