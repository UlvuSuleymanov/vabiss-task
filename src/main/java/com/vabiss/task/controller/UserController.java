package com.vabiss.task.controller;

import com.vabiss.task.model.request.LoginRequestModel;
import com.vabiss.task.model.request.SignUpRequestModel;
import com.vabiss.task.model.response.LoginResponseModel;
import com.vabiss.task.model.response.UserResponseModel;
import com.vabiss.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/account/signup")
    ResponseEntity signUp(@RequestBody @Valid SignUpRequestModel signUpRequestModel) {
        userService.addUser(signUpRequestModel);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/authorize")
    ResponseEntity login(@RequestBody @Valid LoginRequestModel loginRequestModel) {
        return ResponseEntity.ok(userService.login(loginRequestModel));
    }

    @GetMapping("/account/me")
    ResponseEntity showMe() {

        return ResponseEntity.ok(new UserResponseModel("username"));
    }
}
