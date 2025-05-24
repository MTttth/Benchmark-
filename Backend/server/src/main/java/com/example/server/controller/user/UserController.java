package com.example.server.controller.user;

import com.example.commom.constant.JwtClaimsConstant;
import com.example.commom.properties.JwtProperties;
import com.example.commom.result.PageResult;
import com.example.commom.result.Result;
import com.example.commom.utils.JwtUtil;
import com.example.pojo.dto.UserPageQuery;
import com.example.pojo.entity.User;
import com.example.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.pojo.dto.UserLoginDTO;
import com.example.pojo.vo.UserLoginVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("员工登录：{}", userLoginDTO);
        if (userLoginDTO.getType() == 0){
            if (userLoginDTO.getUsername().equals("admin") || userLoginDTO.getPassword().equals("123456")){
                Map<String, Object> claims = new HashMap<>();
                claims.put(JwtClaimsConstant.EMP_ID, 0);
                String token = JwtUtil.createJWT(
                        jwtProperties.getAdminSecretKey(),
                        jwtProperties.getAdminTtl(),
                        claims);
                return Result.success(UserLoginVO.builder().id(0L)
                        .username(userLoginDTO.getUsername())
                        .name("sss")
                        .token(token)
                        .type(0)
                        .build());
            }
        }
        User user = userService.login(userLoginDTO);
        
        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .token(token)
                .type(1)
                .build();


        return Result.success(userLoginVO);
    }

    @GetMapping("/page")
    public Result<PageResult> getUserList(UserPageQuery userPageQuery) {

        PageResult pageResult = userService.pageQuery(userPageQuery);
        return Result.success(pageResult);
    }
    @PostMapping("/register")
    public Result register(@RequestBody User user) {

        userService.userRegister(user);
        return Result.success();

    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable("id") Long id){
        userService.deleteById(id);
        return Result.success();
    }

}
