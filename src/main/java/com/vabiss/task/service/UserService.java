package com.vabiss.task.service;

import com.vabiss.task.model.request.LoginRequestModel;
import com.vabiss.task.model.request.SignUpRequestModel;
import com.vabiss.task.model.response.LoginResponseModel;

public interface UserService {
    void addUser(SignUpRequestModel signUpRequestModel);
    LoginResponseModel login(LoginRequestModel loginRequestModel);
}
