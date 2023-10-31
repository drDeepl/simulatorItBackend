package ru.simbirgo.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="description")
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="level_difficult_id", referencedColumnName = "id")
    private LevelDifficult levelDifficult;


    @Column(name="is_complete")
    private Boolean isComplete;

    @ManyToOne(fetch = FetchType.LAZY)
    private Profession profession;




}
