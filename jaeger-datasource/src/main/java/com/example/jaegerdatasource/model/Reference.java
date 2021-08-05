package com.example.jaegerdatasource.model;

import lombok.Data;

@Data
public class Reference {

    private String refType;

    private String traceID;

    private String spanID;
}
