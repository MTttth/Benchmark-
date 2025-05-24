package com.example.server.mapper;

import com.example.pojo.dto.ConnectUserDTO;
import com.example.pojo.vo.UpdateDatebaseConfigVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface DatabaseMapper {

    @Update("SET GLOBAL wait_timeout = #{timeout}")
    void updateWaitTimeout(long timeout);

    @Update("SET GLOBAL innodb_buffer_pool_size = #{bufferSize}")
    void updateBufferSize(long bufferSize);

    @Select("SHOW VARIABLES LIKE 'datadir'")
    Map<String, String> getPartition();

    @Select("SHOW PROCESSLIST;")
    List<ConnectUserDTO> getConnectUserInfo();

}
