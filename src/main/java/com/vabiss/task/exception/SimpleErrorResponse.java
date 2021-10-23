package com.vabiss.task.exception;

import lombok.Data;

@Data
public class SimpleErrorResponse {
    private String title;
    private String detail;
}
