package com.vabiss.task.model.request;

import com.vabiss.task.validation.NotDublicateUsername;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SignUpRequestModel {
    @NotDublicateUsername
    @NotBlank(message = "İstifadəçi adı boş ola bilməz")
    private String username;

    @NotBlank(message = "Şifrə boş ola bilməz")
    private String password;
}
