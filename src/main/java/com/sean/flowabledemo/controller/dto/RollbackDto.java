package com.sean.flowabledemo.controller.dto;

import lombok.Data;

@Data
public class RollbackDto {
    String currentTaskId;
    String targetTaskId;
}
