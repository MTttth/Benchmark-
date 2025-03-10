package com.example.server.service.Impl;

import com.example.pojo.dto.UserLoginDTO;
import com.example.pojo.entity.User;
import com.example.server.exception.AccountNotFoundException;
import com.example.server.exception.PasswordErrorException;
import com.example.server.mapper.UserMapper;
import com.example.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User login(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        User user = userMapper.getByUsername(username);
        if (user == null) {
            throw new AccountNotFoundException("账户不存在");
        }
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        if (!password.equals(user.getPassword())) {
            throw new PasswordErrorException("密码错误");
        }



        return user;
    }
}
