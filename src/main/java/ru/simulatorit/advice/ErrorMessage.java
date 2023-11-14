package ru.simbirgo.advice;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
class ErrorMessage {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;


}