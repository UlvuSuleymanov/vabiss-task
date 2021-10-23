package com.vabiss.task.exception;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
public class CustomFieldError {
    private String title;
    private Map<String,String> violations = new HashMap<>();
}
