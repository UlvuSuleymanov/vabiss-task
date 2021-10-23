package com.vabiss.task.service.impl;

import com.vabiss.task.entity.User;
import com.vabiss.task.model.request.LoginRequestModel;
import com.vabiss.task.model.request.SignUpRequestModel;
import com.vabiss.task.model.response.LoginResponseModel;
import com.vabiss.task.model.response.UserResponseModel;
import com.vabiss.task.repository.UserRepository;
import com.vabiss.task.service.JwtService;
import com.vabiss.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
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
            throw new AuthenticationCredentialsNotFoundException("Istifadəçi adı və ya şifrə yanlışdır");
        return new LoginResponseModel(jwtService.generateAccessToken(user.get().getId()));

    }

    @Override
    public UserResponseModel getCurrentUser() {
        String idString = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        Long id = Long.valueOf(idString);
        Optional<User> user  = userRepository.findById(id);
        return new UserResponseModel(user.get().getUsername());
    }
}
