package net.pluriel.gestionApp.Reposotories;

import net.pluriel.gestionApp.Models.Admin;
import net.pluriel.gestionApp.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer> {
    Optional<Admin> findByUsernameAndPassword(String username, String password);
}
