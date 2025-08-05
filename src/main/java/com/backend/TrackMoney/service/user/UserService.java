package com.backend.TrackMoney.service.user;

import com.backend.TrackMoney.dto.LoginDTO;
import com.backend.TrackMoney.dto.SignupResponseDTO;
import com.backend.TrackMoney.dto.UserDTO;
import com.backend.TrackMoney.entity.User;
import com.backend.TrackMoney.exception.TrackMoneyException;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    public SignupResponseDTO registerUser(UserDTO userDTO) throws TrackMoneyException;

    public UserDTO loginUser(LoginDTO loginDTO) throws TrackMoneyException;

    public UserDTO getUserByEmail(String email) throws TrackMoneyException;

    public User getCurrentUser();


}
