package ru.simbirgo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.simbirgo.models.Character;
import ru.simbirgo.models.User;

import java.util.List;
import java.util.Optional;



@Repository
public interface AccountRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);


    @Query(value="SELECT * FROM users", nativeQuery = true)
    List<User> findAccounts();
}
