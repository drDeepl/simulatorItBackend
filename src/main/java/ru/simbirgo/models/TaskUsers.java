package ru.simbirgo.models;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirgo.models.serializers.TaskSerializer;

@Entity
@Data
@NoArgsConstructor
@Table(name="tasks_users")
@JsonSerialize(using= TaskSerializer.class)
public class TaskUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="is_complete")
    private Boolean isComplete;

    @ManyToOne(fetch = FetchType.LAZY)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
