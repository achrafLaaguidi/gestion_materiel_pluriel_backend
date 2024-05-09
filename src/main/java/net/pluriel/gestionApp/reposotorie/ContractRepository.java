package net.pluriel.gestionApp.reposotorie;

import net.pluriel.gestionApp.models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {
    Optional<Contract> findByCodeContract(String code);
}
