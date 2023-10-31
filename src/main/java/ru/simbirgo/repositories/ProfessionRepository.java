package ru.simbirgo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirgo.models.Profession;
import ru.simbirgo.models.User;

import java.util.Optional;

public interface ProfessionRepository extends JpaRepository<Profession, Long> {
    Optional<Profession> findByName(String name);

}
