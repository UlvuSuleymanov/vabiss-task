package com.vabiss.task.service;

public interface JwtService {
    String generateAccessToken(Long id);
    Long getIdFromToken(String token);
}