package com.example.server.controller.user;

import com.example.commom.result.Result;
import com.example.pojo.dto.AnalysisDTO;
import com.example.pojo.dto.CustInfoDTO;
import com.example.pojo.dto.ExportDTO;
import com.example.pojo.dto.RegionOrdRevDTO;
import com.example.pojo.vo.AnalysisVO;
import com.example.pojo.vo.CustInfoVO;
import com.example.pojo.vo.DataUploadVO;
import com.example.pojo.vo.RegionOrdRevVO;
import com.example.server.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/data")
@Slf4j
public class DataController {


    @Autowired
    DataService dataService;

    @PostMapping("/upload")
    public Result<?> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("tableName") String tableName) {
//        try {
//            log.info("正在上传数据，表名为："+tableName);
//            tableName = tableName.split(" ")[1];
//            DataUploadVO dataUploadVO = dataService.importData(file, tableName);
//            if (dataUploadVO.getErrorLogs().size() > 0) {
//                return Result.error(dataUploadVO);
//            }
//            return Result.success(dataUploadVO);
//        } catch (Exception e) {
//            return Result.error(e.getMessage());
//        }
        log.info("正在上传数据，表名为："+tableName);
        tableName = tableName.split(" ")[1];
        DataUploadVO dataUploadVO = dataService.importData(file, tableName);
        if (!dataUploadVO.getErrorLogs().isEmpty()) {
            return Result.error(dataUploadVO);
        }
        return Result.success(dataUploadVO);
    }

    @PostMapping("/export")
    public ResponseEntity exportData(@RequestBody ExportDTO request) throws Exception {
        String tableName = request.getTableName().split(" ")[1];
        System.out.println(tableName);
        Resource resource = switch (request.getFileType().toLowerCase()) {
            case "xlsx" -> dataService.generateExcel(tableName);
            case "txt" -> dataService.generateTextFile(tableName);
            default -> throw new IllegalArgumentException("Unsupported file type");
        };

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + tableName + "." + request.getFileType() + "\"")
                .body(resource);

    }

    @PostMapping("/getRegionRev")
    public Result<?> getRegionRev(@RequestBody RegionOrdRevVO regionOrdRevVO) {
        RegionOrdRevDTO regionOrdRevDTO = dataService.getRegionRev(regionOrdRevVO);
        return Result.success(regionOrdRevDTO);
    }

    @PostMapping("/getCustInfo")
    public Result<?> getCustInfo(@RequestBody CustInfoVO custInfoVO) {
        CustInfoDTO custInfoDTO = dataService.getCustInfo(custInfoVO);
        return Result.success(custInfoDTO);
    }

    @GetMapping("/getAllPartType")
    public Result<?> getAllPartType() {
        List<String> allPartType = dataService.getAllPartType();
        return Result.success(allPartType);
    }
    @PostMapping("/analysisData")
    public Result<?> analysisData(@RequestBody AnalysisVO analysisVO) {
        AnalysisDTO analysisDTO = dataService.getAnalysis(analysisVO);
        return Result.success(analysisDTO);
    }



}
