package com.example.pojo.dto;

import com.example.pojo.general.NationProfit;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class AnalysisDTO {
    List<NationProfit> nationProfits;
    double time;
    List<ExplianPlan> explianPlans;
}
