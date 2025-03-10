package com.example.server.service;

import com.example.pojo.dto.UserLoginDTO;
import com.example.pojo.entity.User;

public interface UserService {

    User login(UserLoginDTO userLoginDTO);
}
