package com.example.pojo.vo;

import com.example.pojo.general.ErrorLog;
import com.example.pojo.general.ImportLog;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class DataUploadVO {
    private int rowsInserted;
    private ImportLog importLog;
    private List<ErrorLog> errorLogs;
}
