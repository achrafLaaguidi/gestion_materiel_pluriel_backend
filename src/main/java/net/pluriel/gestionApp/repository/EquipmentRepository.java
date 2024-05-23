package net.pluriel.gestionApp.repository;

import net.pluriel.gestionApp.entity.Equipment_Repair;
import net.pluriel.gestionApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EquipmentRepository extends JpaRepository<Equipment_Repair,Integer> {
    Optional<Equipment_Repair> findBySeriesNumber(String seriesNumber);
    List<Equipment_Repair> findByTechnicianOrderByEntryDateDesc(User technician);
    List<Equipment_Repair> findByEntreeByOrderByEntryDateDesc(String username);
    List<Equipment_Repair> findByIsAcceptedOrderByEntryDateDesc(boolean isAccepted);
    List<Equipment_Repair> findByEntryDate(String entryDate);
    List<Equipment_Repair> findByTechnicianAndIsAcceptedOrderByEntryDateDesc(User technician,boolean isAccepted);
}
