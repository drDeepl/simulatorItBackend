package ru.simbirgo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirgo.models.Dialogue;

public interface DialogueRepository extends JpaRepository<Dialogue, Long> {
}
