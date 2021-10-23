package com.vabiss.task.service.impl;

import com.vabiss.task.entity.User;
import com.vabiss.task.model.request.LoginRequestModel;
import com.vabiss.task.model.request.SignUpRequestModel;
import com.vabiss.task.model.response.LoginResponseModel;
import com.vabiss.task.repository.UserRepository;
import com.vabiss.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(SignUpRequestModel signUpRequestModel) {
        User user = new User(signUpRequestModel);
        userRepository.save(user);
    }

    @Override
    public LoginResponseModel login(LoginRequestModel loginRequestModel) {
        Optional<User> user = userRepository.findByUsername(loginRequestModel.getUsername());
        if(!user.isPresent() || !user.get().getPassword().equals(loginRequestModel.getPassword()))
            throw new EntityNotFoundException("İstifadəçi adı və ya şifrə yanlışdır");
        return new LoginResponseModel("token");

    }
}
