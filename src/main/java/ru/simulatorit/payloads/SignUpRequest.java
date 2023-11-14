package ru.simbirgo.payloads;

import lombok.Data;

@Data
public class SignUpRequest {

    private String username;
    private String password;
    private String gender;
    private Boolean isAdmin;

}
