package com.example.pojo.dto;


import lombok.Builder;
import lombok.Data;


import java.util.List;
@Builder
@Data
public class DatabaseInfoDTO {
    String host;
    List<ConnectUserDTO> user;
    String partition;
}


