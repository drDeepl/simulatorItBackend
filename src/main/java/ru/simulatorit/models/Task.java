package ru.simbirgo.models;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirgo.models.serializers.TaskSerializer;

@Entity
@Data
@NoArgsConstructor
@Table(name="tasks")
@JsonSerialize(using= TaskSerializer.class)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Profession profession;




}
