package com.example.server.controller.user;

import com.example.commom.result.Result;
import com.example.pojo.dto.DatabaseInfoDTO;
import com.example.pojo.vo.UpdateDatebaseConfigVO;
import com.example.server.service.DataBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/db")
public class DatabaseController {
    @Autowired
    DataBaseService dbService;

    @GetMapping("/info")
    public Result<DatabaseInfoDTO> getDatabaseInfo() {

        DatabaseInfoDTO databaseInfoDTO = dbService.getDatabaseInfo();
        return Result.success(databaseInfoDTO);
    }

    @PostMapping("/update-config")
    public Result updateConfig(@RequestBody UpdateDatebaseConfigVO config) {
        log.info("更新数据库配置",config);
        dbService.updateConfig(config);

        return Result.success();
    }
}
