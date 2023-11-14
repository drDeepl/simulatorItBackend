package ru.simbirgo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.simbirgo.models.Answer;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer,Long> {

    @Query(value="SELECT text FROM answers WHERE dialogue_text_id = :dialogueTextId", nativeQuery = true)
    List<String> findAnswersByDialogueTextId(@Param("dialogueTextId") Long dialogueTextId);
}
