package net.pluriel.gestionApp.Reposotories;

import net.pluriel.gestionApp.Models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {
    Optional<Contract> findByCodeContract(String code);
}
