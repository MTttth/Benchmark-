package com.example.server.service.Impl;

import com.example.commom.exception.DataValidationException;
import com.example.commom.utils.JdbcArgsConverter;
import com.example.pojo.dto.AnalysisDTO;
import com.example.pojo.dto.CustInfoDTO;
import com.example.pojo.dto.ExplianPlan;
import com.example.pojo.dto.RegionOrdRevDTO;
import com.example.pojo.general.*;
import com.example.pojo.vo.AnalysisVO;
import com.example.pojo.vo.CustInfoVO;
import com.example.pojo.vo.DataUploadVO;
import com.example.pojo.vo.RegionOrdRevVO;
import com.example.server.mapper.DataMapper;
import com.example.server.service.DataService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.IntStream;


@Service
public class DataServiceImpl implements DataService {
    @Autowired
    private DataMapper dataMapper;

    @Autowired
    private CleanerFactory cleanerFactory;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public DataUploadVO importData(MultipartFile file, String tableName){
        DataCleaner<?> cleaner = cleanerFactory.getCleaner(tableName);
        // 1. 解析 .tbl 文件
        List<Object[]> batchArgs = new ArrayList<>(50);
        int sum = 0;
        List<ErrorLog> errorLogs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {

                Object entity = null;
                try {
                    entity = cleaner.clean(line.split("\\|",-1));
                } catch (DataValidationException e) {
                    ErrorLog errorLog = new ErrorLog();
                    errorLog.setError(e.getMessage());
                    errorLog.setLine(sum+batchArgs.size()+1);
                    errorLog.setTableName(tableName);
                    errorLogs.add(errorLog);
                    sum += 1;
                    continue;
                }

                batchArgs.add(convertToJdbcArgs(entity));

                if (batchArgs.size() >= 50) {
                    String sql = generateInsertSQL(tableName, batchArgs.get(0).length);
                    int[] updateCounts = jdbcTemplate.batchUpdate(sql, batchArgs);
                    sum += Arrays.stream(updateCounts).sum();
                    batchArgs.clear();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(!batchArgs.isEmpty()){
            String sql = generateInsertSQL(tableName, batchArgs.get(0).length);
            int[] updateCounts = jdbcTemplate.batchUpdate(sql, batchArgs);
            sum += Arrays.stream(updateCounts).sum();
            batchArgs.clear();
        }
        ImportLog importLog = new ImportLog();
        importLog.setTableName(tableName);
        importLog.setImportTime(LocalDateTime.now());
        importLog.setTotlal(sum - errorLogs.size());
        return DataUploadVO.builder().importLog(importLog)
                .errorLogs(errorLogs)
                .rowsInserted(sum - errorLogs.size()).build();
    }

    private void createHeaderRow(XSSFSheet sheet, List<ColumnMeta> columns) {
        Row headerRow = sheet.createRow(0);
        IntStream.range(0, columns.size())
                .forEach(i -> headerRow.createCell(i).setCellValue(columns.get(i).getColumnName()));
    }


    private Object[] convertToJdbcArgs(Object entity) {
        return JdbcArgsConverter.convert(entity);
    }
    private String generateInsertSQL(String tableName, int columnCount) {
        String placeholders = String.join(",", Collections.nCopies(columnCount, "?"));
        return "INSERT INTO " + tableName + " VALUES (" + placeholders + ")";
    }


    public Resource generateExcel(String tableName) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(tableName);

        // 获取列信息
        List<ColumnMeta> columns = dataMapper.getColumns(tableName);
        createHeaderRow(sheet, columns);

        // 使用 MyBatis 流式处理
        dataMapper.streamTableData(tableName, resultContext -> {
            Map<String, Object> rowData = resultContext.getResultObject();
            writeDataRow(sheet, columns, rowData);
        });

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            workbook.write(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ByteArrayResource(out.toByteArray());
    }

    public Resource generateTextFile(String tableName) {
        // 创建字符串构建器
        StringBuilder contentBuilder = new StringBuilder();

        List<ColumnMeta> columns = dataMapper.getColumns(tableName);
        // 添加表头（列名）
        for (int i = 0; i < columns.size()-1; i++) {
            contentBuilder.append(columns.get(i).getColumnName());
            contentBuilder.append("|");
        }
        contentBuilder.append(columns.get(columns.size()-1).getColumnName()).append("\n");

        dataMapper.streamTableData(tableName, resultContext -> {
            Map<String, Object> rowData = resultContext.getResultObject();
            for (int i = 0; i < columns.size(); i++) {
                Object value = rowData.get(columns.get(i).getColumnName());
                contentBuilder.append(value != null ? value : ""); // 处理null值
                if (i < columns.size()) {
                    contentBuilder.append("|"); // 列分隔符
                }
            }
            contentBuilder.append("\n"); // 行结束符
        });

        // 转换为字节数组资源（UTF-8编码）
        return new ByteArrayResource(
                contentBuilder.toString().getBytes(StandardCharsets.UTF_8)
        );
    }

    @Override
    public RegionOrdRevDTO getRegionRev(RegionOrdRevVO regionOrdRevVO) {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate date = LocalDate.parse(regionOrdRevVO.getDate(), formatter);
        List<NationRev> nationRevs = dataMapper.getRegionRev(regionOrdRevVO.getRname(),date);
        return RegionOrdRevDTO.builder()
                .nationRevs(nationRevs).build();
    }

    @Override
    public CustInfoDTO getCustInfo(CustInfoVO custInfoVO) {
        UserInfoBase userInfoBase = dataMapper.getCustBaseInfo(custInfoVO.getCname());

        List<OrderInfo> orderInfos = dataMapper.getOrderInfos(custInfoVO.getCname());

        return CustInfoDTO.builder()
                .orderInfos(orderInfos)
                .userInfoBase(userInfoBase).build();
    }

    @Override
    public List<String> getAllPartType() {
        return dataMapper.getAllPartType();
    }

    @Override
    public AnalysisDTO getAnalysis(AnalysisVO analysisVO) {
        long start = System.currentTimeMillis();
        List<NationProfit> nationProfits = dataMapper.getNationProfits(analysisVO.getParttpye());
        long end = System.currentTimeMillis();
        double seconds = (end - start) / 1000.0;
        List<ExplianPlan> explianPlans = dataMapper.getExplainPlan(analysisVO.getParttpye());
        return AnalysisDTO.builder()
                .nationProfits(nationProfits)
                .time(seconds)
                .explianPlans(explianPlans).build();
    }

    private void writeDataRow(XSSFSheet sheet, List<ColumnMeta> columns, Map<String, Object> rowData) {
        int rowNum = sheet.getLastRowNum() + 1;
        Row row = sheet.createRow(rowNum);
        IntStream.range(0, columns.size())
                .forEach(i -> {
                    Object value = rowData.get(columns.get(i).getColumnName());
                    row.createCell(i).setCellValue(value != null ? value.toString() : "");
                });
    }
}
