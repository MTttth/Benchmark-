package com.example.server.service;

import com.example.pojo.dto.AnalysisDTO;
import com.example.pojo.dto.CustInfoDTO;
import com.example.pojo.dto.ExportDTO;
import com.example.pojo.dto.RegionOrdRevDTO;
import com.example.pojo.vo.AnalysisVO;
import com.example.pojo.vo.CustInfoVO;
import com.example.pojo.vo.DataUploadVO;
import com.example.pojo.vo.RegionOrdRevVO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DataService {

    DataUploadVO importData(MultipartFile file, String tableName);


    Resource generateExcel(String tableName);
    Resource generateTextFile(String tableName);

    RegionOrdRevDTO getRegionRev(RegionOrdRevVO regionOrdRevVO);

    CustInfoDTO getCustInfo(CustInfoVO custInfoVO);

    List<String> getAllPartType();

    AnalysisDTO getAnalysis(AnalysisVO analysisVO);
}
