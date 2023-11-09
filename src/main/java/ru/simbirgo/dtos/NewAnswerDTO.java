package ru.simbirgo.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewAnswerDTO {
    private Long answerId;
    private Long dialogueTextId;
    private String textAnswer;
}
