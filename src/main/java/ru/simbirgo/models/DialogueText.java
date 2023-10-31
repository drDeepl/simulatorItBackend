package ru.simbirgo.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name="dialogue_texts")
public class DialogueText {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Dialogue dialogue;

    @Column(name="text")
    private String text;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="answer_id")
    private List<Answer> answers = new ArrayList<>();



}
