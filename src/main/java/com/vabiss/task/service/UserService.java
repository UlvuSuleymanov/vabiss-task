package com.vabiss.task.service;

import com.vabiss.task.model.request.LoginRequestModel;
import com.vabiss.task.model.request.SignUpRequestModel;
import com.vabiss.task.model.response.LoginResponseModel;
import com.vabiss.task.model.response.UserResponseModel;

public interface UserService {
    void addUser(SignUpRequestModel signUpRequestModel);
    LoginResponseModel login(LoginRequestModel loginRequestModel);
    UserResponseModel getCurrentUser();
}
