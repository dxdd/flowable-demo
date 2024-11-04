package com.sean.flowabledemo.controller.dto;

import lombok.Data;

@Data
public class StartProcessRepresentationDto {
    private String assignee;
    private String processDefinitionKey;
}
