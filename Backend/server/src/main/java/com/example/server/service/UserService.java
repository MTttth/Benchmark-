package com.example.server.service;

import com.example.commom.result.PageResult;
import com.example.pojo.dto.UserLoginDTO;
import com.example.pojo.dto.UserPageQuery;
import com.example.pojo.entity.User;

public interface UserService {

    User login(UserLoginDTO userLoginDTO);

    PageResult pageQuery(UserPageQuery userPageQuery);

    void userRegister(User user);

    void deleteById(Long id);
}
