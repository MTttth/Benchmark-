package com.example.server.mapper;

import com.example.pojo.dto.UserPageQuery;
import com.example.pojo.entity.User;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where username = #{username}")
    User getByUsername(String username);

    @Select("select * from user")
    Page<User> pageQuery(UserPageQuery userPageQuery);


    @Insert("insert into user(username, password, name,type) values (#{username}, #{password},#{name},#{type})")
    void addUser(User user);

    @Delete("delete from user where id = #{id}")
    void deleteById(Long id);
}
