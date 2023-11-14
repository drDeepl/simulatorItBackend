package ru.simbirgo.dtos;

import lombok.Data;

@Data
public class DialogueTextDTO {
    private Long dialogueTextId;
    private Long dialogueId;
    private String text;
    private String[] answers;
}
