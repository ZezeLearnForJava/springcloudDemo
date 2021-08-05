package com.example.jaegerdatasource.model;

import lombok.Data;

import java.util.List;

@Data
public class Log {

    private Long timestamp;

    private List<Field> fields;
}
