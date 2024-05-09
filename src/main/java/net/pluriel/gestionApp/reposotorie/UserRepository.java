package net.pluriel.gestionApp.reposotorie;

import net.pluriel.gestionApp.models.Status;
import net.pluriel.gestionApp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    List<User> findAllByOrderByIdDesc();
    Optional<User> findByUsernameAndPassword(@RequestParam String username, @RequestParam String password);
    Optional<User> findByUsername(String username);
    List<User> findByStatus(Status status);

}
