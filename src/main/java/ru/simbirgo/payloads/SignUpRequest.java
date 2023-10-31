package ru.simbirgo.payloads;

import lombok.Data;

@Data
public class SignUpRequest {

    private String username;
    private String password;

}
