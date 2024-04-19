package net.pluriel.gestionApp.Reposotories;

import net.pluriel.gestionApp.Models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ClientRepository extends JpaRepository<Client,Integer> {
    Optional<Client> findByICE(Integer ice);
}
