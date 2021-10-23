package com.vabiss.task.validation;


import com.vabiss.task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotDublicateUsernameValidator implements ConstraintValidator<NotDublicateUsername, String> {

    private final UserRepository userRepository;

    @Autowired
    public NotDublicateUsernameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        return !userRepository.existsByUsername(s);
    }
}

