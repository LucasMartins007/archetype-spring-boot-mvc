package com.lucasmartins.common.exception;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DomainExceptionResponse {

    private String message;

    private String trace;

    private Integer status;

    private Date timeStamp;

    private List<String> details;

}
