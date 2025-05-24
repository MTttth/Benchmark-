package com.example.server.service.Impl;

import com.example.commom.exception.AccountAlreadyExit;
import com.example.commom.exception.AccountNotFoundException;
import com.example.commom.exception.PasswordErrorException;
import com.example.commom.result.PageResult;
import com.example.pojo.dto.UserLoginDTO;
import com.example.pojo.dto.UserPageQuery;
import com.example.pojo.entity.User;

import com.example.server.mapper.UserMapper;
import com.example.server.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
        if (!password.equals(user.getPassword())) {
            throw new PasswordErrorException("密码错误");
        }



        return user;
    }

    @Override
    public PageResult pageQuery(UserPageQuery userPageQuery) {

        PageHelper.startPage(userPageQuery.getPage(),userPageQuery.getPageSize());

        Page<User> page = userMapper.pageQuery(userPageQuery);

        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void userRegister(User user) {
        User t = userMapper.getByUsername(user.getUsername());
        if (t != null) {
            throw new AccountAlreadyExit("账户已经存在");
        }
        userMapper.addUser(user);
    }

    @Override
    public void deleteById(Long id) {
        userMapper.deleteById(id);
    }
}
