package com.vabiss.task.entity;

import com.vabiss.task.model.request.SignUpRequestModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    public User(SignUpRequestModel signUpRequestModel){
        username=signUpRequestModel.getUsername();
        password=signUpRequestModel.getPassword();
    }
}
