package com.example.pojo.dto;

import lombok.Data;

@Data
public class ConnectUserDTO {
    private String User;
    private String Host;
    private String db;
    private String Command;
    private Integer Time;
}
