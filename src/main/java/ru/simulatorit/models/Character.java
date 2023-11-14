package ru.simbirgo.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirgo.models.serializers.CharacterSerializer;

@Entity
@Data
@NoArgsConstructor
@JsonSerialize(using= CharacterSerializer.class)
@Table(name="characters")
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="name", unique = true, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Profession profession;
}
