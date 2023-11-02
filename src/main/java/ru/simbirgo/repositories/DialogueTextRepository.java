package ru.simbirgo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.simbirgo.models.DialogueText;

import java.util.List;

@Repository
public interface DialogueTextRepository extends JpaRepository<DialogueText, Long> {
    @Query(value="SELECT * FROM dialogue_texts WHERE dialogue_id = :dialogueId", nativeQuery = true)
    List<DialogueText> findAllByDialogueId(@Param("dialogueId") Long dialogueId);
}
