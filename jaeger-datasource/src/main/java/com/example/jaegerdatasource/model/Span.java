package com.example.jaegerdatasource.model;


import lombok.Data;

import java.util.List;

@Data
public class Span {

    private String traceID;

    private String SpanID;

    private int flags;

    private String operationName;

    private List<Reference> references;

    private Long startTime;

    private Long duration;

    private List<Tag> tags;


}
