package ru.simbirgo.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirgo.models.serializers.AnswerSerializer;
import ru.simbirgo.models.serializers.DialogueTextSerializer;

@Entity
@Data
@NoArgsConstructor
@JsonSerialize(using= AnswerSerializer.class)
@Table(name="answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private DialogueText dialogueText;

    @Column(name="text")
    private String textAnswer;


}
