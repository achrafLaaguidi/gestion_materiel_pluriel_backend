package net.pluriel.gestionApp.Reposotories;

import net.pluriel.gestionApp.Models.Equipment_Repair;
import net.pluriel.gestionApp.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EquipmentRepository extends JpaRepository<Equipment_Repair,Integer> {
    Optional<Equipment_Repair> findBySeriesNumber(String seriesNumber);
    List<Equipment_Repair> findByTechnician(User technician);
    List<Equipment_Repair> findByEntreeBy(String username);
    List<Equipment_Repair> findByIsAccepted(boolean isAccepted);
    List<Equipment_Repair> findByTechnicianAndIsAccepted(User technician,boolean isAccepted);
}
