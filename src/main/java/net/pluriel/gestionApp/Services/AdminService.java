package net.pluriel.gestionApp.Services;

import jakarta.transaction.Transactional;
import net.pluriel.gestionApp.DTO.TechnicianData;
import net.pluriel.gestionApp.Errors.*;
import net.pluriel.gestionApp.Models.*;
import net.pluriel.gestionApp.Models.User;
import net.pluriel.gestionApp.Reposotories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AdminService {
    final
    AdminRepository repository;
    final
    UserRepository userRepository;
    final ClientRepository clientRepository;
    final ContractRepository contractRepository;
    final EquipmentRepository equipmentRepository;


    @Autowired
    public AdminService(AdminRepository repository, UserRepository userRepository, ClientRepository clientRepository, ContractRepository contractRepository, EquipmentRepository equipmentRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.contractRepository = contractRepository;
        this.equipmentRepository = equipmentRepository;
    }







    public List<User> listUser() {
        List<User> userList = userRepository.findAll();
        Optional<Admin> adminOptional = repository.findById(0);
        adminOptional.ifPresent(admin -> {
            userList.removeIf(user -> user.getId().equals(admin.getId()));
        });
        return userList;
    }
    public List<User> listUserActive(){return userRepository.findByStatus(Status.ACTIVE);}




    @Transactional
    public Equipment_Repair annonceMaterielRepair(Equipment_Repair equipmentRepair) {
        Optional<Equipment_Repair> equipmentRepairOptional=equipmentRepository.findBySeriesNumber(equipmentRepair.getSeriesNumber());
        if (equipmentRepairOptional.isPresent()) {
            Equipment_Repair equipment_repair=equipmentRepairOptional.get();
                  if (equipmentRepair.getTechnicianData() != null) {
                     if(equipmentRepair.getTechnicianData().getId()==0){
                         Optional<Admin> adminOptional=repository.findById(equipmentRepair.getTechnicianData().getId());
                         if (adminOptional.isPresent()) {
                             Admin admin=adminOptional.get();
                             equipment_repair.setTechnician(new User(admin.getId(),admin.getUsername()));

                         }
                         else {
                             throw new NotFoundException(
                                     String.format("Technician [%s] not found!", equipment_repair.getTechnicianData().getUsername()));
                         }
                     }
                     else{
                         Optional<User> technicianOptional = userRepository.findByUsername(equipmentRepair.getTechnicianData().getUsername());
                         if (technicianOptional.isPresent()) {
                             User technician = technicianOptional.get();

                             equipment_repair.setTechnician(technician);

                             technician.getEquipmentRepairList().add(equipment_repair);
                             userRepository.save(technician);
                         } else {
                             throw new NotFoundException(
                                     String.format("Technician [%s] not found!", equipment_repair.getTechnicianData().getUsername()));
                         }
                     }

                }
                  return equipmentRepository.save(equipment_repair);
        }
        else{
            throw new NotFoundException(String.format("This Equipment [%s] is not found!", equipmentRepair.getSeriesNumber()));
        }

    }

    @Transactional
    public Equipment_Repair modifyAnnonceMaterialReapair(Equipment_Repair equipmentRepair) {
        Optional<Equipment_Repair> equipmentRepairOptional=equipmentRepository.findById(equipmentRepair.getId());
        if(equipmentRepairOptional.isPresent()){
            Equipment_Repair equipment_repair_existing=equipmentRepairOptional.get();

            if(equipmentRepair.getSeriesNumber()!=null && !equipmentRepair.getSeriesNumber().equals(equipment_repair_existing.getSeriesNumber())) {
                if (equipmentRepository.findBySeriesNumber(equipmentRepair.getSeriesNumber()).isPresent()) {
                    throw new ConflictException(String.format("This Equipment [%s] already exists!", equipmentRepair.getSeriesNumber()));
                }
                equipment_repair_existing.setSeriesNumber(equipmentRepair.getSeriesNumber());
            }
            if(equipmentRepair.getTechnicianData()!=null ) {
                User technician = userRepository.findByUsername(equipmentRepair.getTechnicianData().getUsername())
                        .orElseThrow(() -> new NotFoundException(String.format("Technician [%s] not found!", equipmentRepair.getTechnician().getUsername())));

                equipment_repair_existing.setTechnician(technician);
                equipment_repair_existing.setTechnicianData(new TechnicianData(technician.getId(), technician.getUsername()));
            }
            else{
                equipment_repair_existing.setTechnician(null);
            }
            if(equipmentRepair.getClientData()!=null ) {
                Client client = clientRepository.findById(equipmentRepair.getClientData().getId())
                        .orElseThrow(() -> new NotFoundException(String.format("Client with ID [%d] not found!", equipmentRepair.getClientData().getId())));
                equipment_repair_existing.setClient(client);
                equipment_repair_existing.setClientData(client);
            }
            if(equipmentRepair.getTechnicianLivreur() != null)
               equipment_repair_existing.setTechnicianLivreur(equipmentRepair.getTechnicianLivreur());
            if(equipmentRepair.getName() != null)
               equipment_repair_existing.setName(equipmentRepair.getName());
            if(equipmentRepair.getType()!=null)
                equipment_repair_existing.setType(equipmentRepair.getType());
            if(equipmentRepair.getEntryDate()!=null)
                equipment_repair_existing.setEntryDate(equipmentRepair.getEntryDate());
            equipmentRepository.save(equipment_repair_existing);
            return equipment_repair_existing;
        }
        else {throw new NotFoundException(String.format("This Equipment [%s] is not found",equipmentRepair.getName()));}
    }

    public Equipment_Repair deleteAnnonceMaterialRepair(int id) {
        Optional<Equipment_Repair> equipmentRepairOptional=equipmentRepository.findById(id);
        if(equipmentRepairOptional.isPresent()){
            Equipment_Repair equipment_repair_existing=equipmentRepairOptional.get();
            equipmentRepository.delete(equipment_repair_existing);
            return equipment_repair_existing;
        }
        else{
            throw new NotFoundException(String.format("This Equipment [%s] is not found",id));
        }
    }

    public List<Equipment_Repair> listMaterialsRepair() {
        List<Equipment_Repair> equipmentRepairList=equipmentRepository.findByIsAccepted(true);
        for(Equipment_Repair equipmentRepair:equipmentRepairList){
            if(equipmentRepair.getTechnician()!=null){
                equipmentRepair.setTechnicianData(new TechnicianData(equipmentRepair.getTechnician().getId(), equipmentRepair.getTechnician().getUsername()));
            }
            if(equipmentRepair.getClient()!=null){
                equipmentRepair.setClientData(equipmentRepair.getClient());
            }
        }
        return equipmentRepairList ;
    }
    public Equipment_Repair modifyMaterialRepair(Equipment_Repair equipmentRepair){
        Optional<Equipment_Repair> equipmentRepairOptional=equipmentRepository.findById(equipmentRepair.getId());
        if(equipmentRepairOptional.isPresent()){
            Equipment_Repair equipment_repair_existing=equipmentRepairOptional.get();

            if(equipmentRepair.getSeriesNumber()!=null && !equipmentRepair.getSeriesNumber().equals(equipment_repair_existing.getSeriesNumber()) ) {
                if (equipmentRepository.findBySeriesNumber(equipmentRepair.getSeriesNumber()).isPresent()) {
                    throw new ConflictException(String.format("This Equipment [%s] already exists!", equipmentRepair.getSeriesNumber()));
                }
                equipment_repair_existing.setSeriesNumber(equipmentRepair.getSeriesNumber());
            }
            if(equipmentRepair.getClientData()!=null ) {
                Client client = clientRepository.findById(equipmentRepair.getClientData().getId())
                        .orElseThrow(() -> new NotFoundException(String.format("Client with ID [%d] not found!", equipmentRepair.getClientData().getId())));
                equipment_repair_existing.setClient(client);
            }
            if(equipmentRepair.getTechnicianData()!=null ) {
                int id=equipmentRepair.getTechnicianData().getId();
                if(id!=-1){
                User user = userRepository.findById(equipmentRepair.getTechnicianData().getId())
                        .orElseThrow(() -> new NotFoundException(String.format("Technicien with ID [%d] not found!", equipmentRepair.getTechnicianData().getId())));
                equipment_repair_existing.setTechnician(user);}
                    else{
                        equipment_repair_existing.setIsAccepted(false);
                        equipment_repair_existing.setTechnician(null);
                    }
            }
            if(equipmentRepair.getTechnicianLivreur() != null)
                equipment_repair_existing.setTechnicianLivreur(equipmentRepair.getTechnicianLivreur());
            if(equipmentRepair.getName() != null)
                equipment_repair_existing.setName(equipmentRepair.getName());
            if(equipmentRepair.getType()!=null)
                equipment_repair_existing.setType(equipmentRepair.getType());
            if(equipmentRepair.getIsGarented()!=null)
                equipment_repair_existing.setIsGarented(equipmentRepair.getIsGarented());
            if(equipmentRepair.getEntryDate()!=null)
                equipment_repair_existing.setEntryDate(equipmentRepair.getEntryDate());
            if(equipmentRepair.getReleaseDate()!=null)
                equipment_repair_existing.setReleaseDate(equipmentRepair.getReleaseDate());
            if(equipmentRepair.getDureeIntervention()!=null)
                equipment_repair_existing.setDureeIntervention(equipmentRepair.getDureeIntervention());
            if(equipmentRepair.getIntervention()!=null)
                equipment_repair_existing.setIntervention(equipmentRepair.getIntervention());
            equipmentRepository.save(equipment_repair_existing);
            return equipment_repair_existing;
        }
        else {throw new NotFoundException(String.format("This Equipment [%s] is not found",equipmentRepair.getName()));}
    }

    public List<Equipment_Repair> listMaterielsLivres() {
        List<Equipment_Repair> equipmentRepairList=equipmentRepository.findByIsAccepted(false);
        for (Equipment_Repair equipmentRepair : equipmentRepairList) {
            if(equipmentRepair.getClient()!=null){
                equipmentRepair.setClientData(equipmentRepair.getClient());
            }
            if(equipmentRepair.getTechnician()!=null){
                User technician=equipmentRepair.getTechnician();
                equipmentRepair.setTechnicianData(new TechnicianData(technician.getId(), technician.getUsername()));
            }

        }
       return equipmentRepairList;
    }
    @Transactional
    public Equipment_Repair validateMaterial(Equipment_Repair equipmentRepair) {
        Optional<Equipment_Repair> equipmentRepairOptional=equipmentRepository.findById(equipmentRepair.getId());
        if(equipmentRepairOptional.isPresent()){
            Equipment_Repair equipment_repair_existing=equipmentRepairOptional.get();
            if(equipment_repair_existing.getReleaseDate()!=null){
            equipment_repair_existing.setReleaseDate(equipmentRepair.getReleaseDate());
            return equipmentRepository.save(equipment_repair_existing);
            }
        }
        else{
            throw new NotFoundException(String.format("This Equipment [%s] is not found",equipmentRepair.getName()));
        }
        return null;
}
}
