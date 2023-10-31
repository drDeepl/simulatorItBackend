package ru.simbirgo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirgo.models.Character;

import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, Long> {

    Optional<Character> findById(Long Long);
    Optional<Character> findByName(String name);


}
