package net.pluriel.gestionApp.Reposotories;

import jakarta.transaction.Transactional;
import net.pluriel.gestionApp.Models.Admin;
import net.pluriel.gestionApp.Models.Equipment_Repair;
import net.pluriel.gestionApp.Models.Status;
import net.pluriel.gestionApp.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsernameAndPassword(@RequestParam String username, @RequestParam String password);
    Optional<User> findByUsername(String username);
    List<User> findByStatus(Status status);

}
