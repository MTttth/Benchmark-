package com.example.pojo.dto;


import com.example.pojo.general.NationRev;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RegionOrdRevDTO {
    private List<NationRev> nationRevs;
}
