package net.pluriel.gestionApp.Services;

import jakarta.transaction.Transactional;
import net.pluriel.gestionApp.Errors.ConflictException;
import net.pluriel.gestionApp.Errors.NotFoundException;
import net.pluriel.gestionApp.Models.*;
import net.pluriel.gestionApp.Reposotories.ClientRepository;
import net.pluriel.gestionApp.Reposotories.EquipmentRepository;
import net.pluriel.gestionApp.Reposotories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    final
    UserRepository repository;
    final EquipmentRepository equipmentRepository;
     final UserRepository userRepository;
    final ClientRepository clientRepository;

    @Autowired
    public UserService(UserRepository repository, EquipmentRepository equipmentRepository, UserRepository userRepository, ClientRepository clientRepository) {
        this.repository = repository;
        this.equipmentRepository = equipmentRepository;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
    }

    public TechnicianData authenticate(User user) {
        Optional<User> userOptional=repository.findByUsernameAndPassword(user.getUsername(),user.getPassword());
        if(userOptional.isPresent()){
            User userExisting=userOptional.get();
            if(userExisting.getStatus().equals(Status.ACTIVE)){
                return new TechnicianData(userExisting.getId(),userExisting.getUsername());
            }
            else{
                throw new NotFoundException(String.format("This User [%s] is INACTIVE", user.getUsername()));
            }
        }
        else {
            throw new NotFoundException("Username or Password is invalid");
        }

    }

    @Transactional
    public Equipment_Repair addMaterielRepair(Equipment_Repair equipmentRepair, int id) {
        if (equipmentRepository.findBySeriesNumber(equipmentRepair.getSeriesNumber()).isPresent()) {
            throw new ConflictException(String.format("This Equipment [%s] already exists!", equipmentRepair.getSeriesNumber()));
        }
        if(equipmentRepair.getClientData()!=null) {
            Optional<Client> clientOptional = clientRepository.findById(equipmentRepair.getClientData().getId());
            if (clientOptional.isPresent()) {
                Client client = clientOptional.get();
                equipmentRepair.setClient(client);
                equipmentRepair.setClientData(client);

                Optional<User> technicianOptional = userRepository.findById(id);
                if (technicianOptional.isPresent()) {
                    User technician = technicianOptional.get();
                    equipmentRepair.setTechnicianLivreur(technician.getUsername());
                } else {
                    throw new NotFoundException(
                            String.format("Technician [%s] not found!", equipmentRepair.getTechnicianData().getUsername()));
                }
                equipmentRepository.save(equipmentRepair);
                client.getEquipmentRepairList().add(equipmentRepair);
                clientRepository.save(client);

            } else {
                throw new NotFoundException(String.format("Client with ID [%d] not found!", equipmentRepair.getClientData().getId()));
            }
        }
        return equipmentRepair;
    }

    public List<Equipment_Repair> listMaterielAdded(int id) {
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Equipment_Repair> equipmentRepairList = equipmentRepository.findByTechnicianLivreur(user.getUsername());
            for (Equipment_Repair equipmentRepair : equipmentRepairList) {
                equipmentRepair.setClientData(equipmentRepair.getClient());
            }
            return equipmentRepairList;
        }
        return null;
    }


    public List<Equipment_Repair> listMaterielDemandeRepair(int id) {
        Optional<User> userOptional =repository.findById(id);
        if(userOptional.isPresent()){
            User user=userOptional.get();
            List<Equipment_Repair> equipmentRepairList=equipmentRepository.findByTechnicianAndIsAccepted(user,false);
            List<Equipment_Repair>equipmentRepairsList=equipmentRepository.findByTechnician(null);
            if(equipmentRepairList!=null){
                for(Equipment_Repair equipmentRepair:equipmentRepairList){
                    equipmentRepair.setTechnicianData(new TechnicianData(user.getId(),user.getUsername()));
                    equipmentRepair.setClientData(equipmentRepair.getClient());
                }
            }
            if(equipmentRepairsList!=null) {
                for (Equipment_Repair equipmentRepair : equipmentRepairsList) {
                    equipmentRepair.setClientData(equipmentRepair.getClient());
                }
                equipmentRepairList.addAll(equipmentRepairsList);
            }
            return equipmentRepairList;
        }
        else{
            throw new NotFoundException("This User is not found");
        }
    }

    @Transactional()
    public Boolean accepteAnnonce(int materielId,int id) {
        Optional<Equipment_Repair> equipmentRepairOptional=equipmentRepository.findById(materielId);
        if(equipmentRepairOptional.isPresent()){
            Equipment_Repair equipmentRepair=equipmentRepairOptional.get();
            equipmentRepair.setIsAccepted(true);
            if(equipmentRepair.getTechnicianData()==null){
                User user=repository.findById(id)
                        .orElseThrow(() -> new NotFoundException(String.format("Technician with[%d] not found!",id)));
                equipmentRepair.setTechnicianData(new TechnicianData(user.getId(), user.getUsername()));
                equipmentRepair.setTechnician(user);
            }
            equipmentRepository.save(equipmentRepair);
            return true;
        }
        else {
            throw new NotFoundException("This Equipment is not found");
        }
    }

    public List<Equipment_Repair> listMaterialsRepair(int id) {
        Optional<User> userOptional =repository.findById(id);
        if(userOptional.isPresent()){
            User user=userOptional.get();
            List<Equipment_Repair> equipmentRepairList=equipmentRepository.findByTechnicianAndIsAccepted(user,true);
            for(Equipment_Repair equipmentRepair:equipmentRepairList){
                equipmentRepair.setTechnicianData(new TechnicianData(user.getId(),user.getUsername()));
                equipmentRepair.setClientData(equipmentRepair.getClient());
            }
            return equipmentRepairList;
        }
        else{
            throw new NotFoundException("This User is not found");
        }
    }



    public Equipment_Repair modifyMaterielRepair(Equipment_Repair equipmentRepair) {
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
                equipment_repair_existing.setClientData(client);
            }
            if(equipmentRepair.getName() != null)
                equipment_repair_existing.setName(equipmentRepair.getName());
            if(equipmentRepair.getType()!=null)
                equipment_repair_existing.setType(equipmentRepair.getType());
            if(equipmentRepair.getIsGarented()!=null)
                equipment_repair_existing.setIsGarented(equipmentRepair.getIsGarented());
            if(equipmentRepair.getEntryDate()!=null)
                equipment_repair_existing.setEntryDate(equipmentRepair.getEntryDate());
            if(equipmentRepair.getDureeIntervention()!=null)
                equipment_repair_existing.setDureeIntervention(equipmentRepair.getDureeIntervention());
            if(equipmentRepair.getIntervention()!=null)
                equipment_repair_existing.setIntervention(equipmentRepair.getIntervention());
            equipmentRepository.save(equipment_repair_existing);
            return equipment_repair_existing;
        }
        else {throw new NotFoundException(String.format("This Equipment [%s] is not found",equipmentRepair.getName()));}
    }



}
