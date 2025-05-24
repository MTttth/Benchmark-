package com.example.server.service;

import com.example.pojo.dto.DatabaseInfoDTO;
import com.example.pojo.vo.UpdateDatebaseConfigVO;

public interface DataBaseService {

    void updateConfig(UpdateDatebaseConfigVO config);


    DatabaseInfoDTO getDatabaseInfo();

}
