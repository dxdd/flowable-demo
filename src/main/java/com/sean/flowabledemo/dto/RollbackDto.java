package com.sean.flowabledemo.dto;

import lombok.Data;

@Data
public class RollbackDto {
    String currentTaskId;
    String targetTaskId;
}
