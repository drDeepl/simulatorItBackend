package ru.simbirgo.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirgo.models.serializers.CharacterSerializer;
import ru.simbirgo.models.serializers.DialogueTextSerializer;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@JsonSerialize(using= DialogueTextSerializer.class)
@Table(name="dialogue_texts")
public class DialogueText {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Dialogue dialogue;

    @Column(name="text")
    private String text;

}
