package com.vabiss.task.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SignUpRequestModel {

    @NotBlank(message = "İstifadəçi adı boş ola bilməz")
    private String username;

    @NotBlank(message = "Şifrə boş ola bilməz")
    private String password;
}
