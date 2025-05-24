package com.example.server.mapper;

import com.example.pojo.dto.ExplianPlan;
import com.example.pojo.general.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.session.ResultHandler;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface DataMapper {

    @Select("SELECT * FROM ${tableName}")
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = 1000)
    @ResultType(Map.class)
    void streamTableData(@Param("tableName") String tableName, ResultHandler<Map<String, Object>> handler);

    @Select("SELECT column_name AS columnName, data_type AS dataType " +
            "FROM information_schema.columns " +
            "WHERE table_schema = DATABASE() AND table_name = #{tableName}")
    List<ColumnMeta> getColumns(@Param("tableName") String tableName);


    List<NationRev> getRegionRev(String rName, LocalDate date);

    UserInfoBase getCustBaseInfo(String cName);

    List<OrderInfo> getOrderInfos(String cName);

    @Select("select distinct part.P_TYPE from part")
    List<String> getAllPartType();

    List<NationProfit> getNationProfits(String partType);

    List<ExplianPlan> getExplainPlan(String partTpye);
}
