package ru.simbirgo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.simbirgo.models.Character;

import java.util.List;
import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, Long> {

    Optional<Character> findById(Long Long);
    Optional<Character> findByName(String name);

    @Query(value = "UPDATE characters SET profession_id = null WHERE profession_id = :professionId",nativeQuery = true)
    void setNullProfessionIdById(@Param("professionId") Long professionId);

    @Query(value="SELECT * FROM characters WHERE profession_id = :professionId", nativeQuery = true)
    List<Character> findAllByProfessionId(@Param("professionId") Long professionId);



}
