package com.example.server.service.Impl;

import com.example.pojo.dto.ConnectUserDTO;
import com.example.pojo.dto.DatabaseInfoDTO;
import com.example.pojo.vo.UpdateDatebaseConfigVO;
import com.example.server.mapper.DatabaseMapper;
import com.example.server.service.DataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

@Service
public class DataBaseServiceImpl implements DataBaseService {
    @Autowired
    DatabaseMapper databaseMapper;

    @Override
    public void updateConfig(UpdateDatebaseConfigVO config) {
        databaseMapper.updateWaitTimeout(config.getTimeout());

        long bufferSize = config.getBufferSize() * 1024 * 1024;

        databaseMapper.updateBufferSize(bufferSize);
    }

    @Override
    public DatabaseInfoDTO getDatabaseInfo() {

        Map<String, String> partitionInfo = databaseMapper.getPartition();

        String partition = partitionInfo.get("Value");
        List<ConnectUserDTO> connectUserDTOs = databaseMapper.getConnectUserInfo();


        String ip = getIp();

        return DatabaseInfoDTO.builder()
                .user(connectUserDTOs)
                .partition(partition)
                .host(ip).build();
    }

    public static String  getIp(){
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        return addr.getHostAddress();
    }
}
