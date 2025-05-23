package net.pluriel.gestionApp.repository;

import net.pluriel.gestionApp.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
@Repository
public interface ClientRepository extends JpaRepository<Client,Integer> {


    Optional<Client> findByDénominationSociale(String denominationSociale);
}
