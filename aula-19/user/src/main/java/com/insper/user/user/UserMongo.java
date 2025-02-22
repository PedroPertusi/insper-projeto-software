package com.insper.user.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document("user")
public class UserMongo {

    @Id
    private String id;
    private String email;
    private String password;
    private List<String> roles;
    private Boolean disabled;
}
