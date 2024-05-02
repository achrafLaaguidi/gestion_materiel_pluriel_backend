package net.pluriel.gestionApp.ControllersAndServices.Material;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.pluriel.gestionApp.DTO.EquipmentRepairDto;
import net.pluriel.gestionApp.DTO.TechnicianData;
import net.pluriel.gestionApp.Errors.ConflictException;
import net.pluriel.gestionApp.Errors.NotFoundException;
import net.pluriel.gestionApp.Models.Client;
import net.pluriel.gestionApp.Models.Equipment_Repair;
import net.pluriel.gestionApp.Models.User;
import net.pluriel.gestionApp.Reposotories.ClientRepository;
import net.pluriel.gestionApp.Reposotories.EquipmentRepository;
import net.pluriel.gestionApp.Reposotories.UserRepository;
import net.pluriel.gestionApp.mappers.DtoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnnonceService {
    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final DtoMapper dtoMapper;

    @Transactional
    public EquipmentRepairDto addAnnonceMaterial(EquipmentRepairDto equipmentRepairDto) {
        Optional<Equipment_Repair> equipmentRepairOptional=equipmentRepository.findBySeriesNumber(equipmentRepairDto.getSeriesNumber());
        if (equipmentRepairOptional.isPresent()) {
            Equipment_Repair equipment_repair=equipmentRepairOptional.get();
            if (equipmentRepairDto.getTechnician() != null) {
                    Optional<User> technicianOptional = userRepository.findByUsername(equipmentRepairDto.getTechnician().getUsername());
                    if (technicianOptional.isPresent()) {
                        User technician = technicianOptional.get();

                        equipment_repair.setTechnician(technician);

                    } else {
                        throw new NotFoundException(
                                String.format("Technician [%s] not found!", equipment_repair.getTechnician().getUsername()));
                    }

            }
            else{
                equipment_repair.setTechnician(null);
            }
            return dtoMapper.toEquipmentDto(equipmentRepository.save(equipment_repair));
        }
        else{
            throw new NotFoundException(String.format("This Equipment [%s] is not found!", equipmentRepairDto.getSeriesNumber()));
        }

    }

    @Transactional
    public EquipmentRepairDto modifyAnnonceMaterial(EquipmentRepairDto equipmentRepairDto) {
        Optional<Equipment_Repair> equipmentRepairOptional=equipmentRepository.findById(equipmentRepairDto.getId());
        if(equipmentRepairOptional.isPresent()){
            Equipment_Repair equipment_repair_existing=equipmentRepairOptional.get();

            if(equipmentRepairDto.getSeriesNumber()!=null && !equipmentRepairDto.getSeriesNumber().equals(equipment_repair_existing.getSeriesNumber())) {
                if (equipmentRepository.findBySeriesNumber(equipmentRepairDto.getSeriesNumber()).isPresent()) {
                    throw new ConflictException(String.format("This Equipment [%s] already exists!", equipmentRepairDto.getSeriesNumber()));
                }
                equipment_repair_existing.setSeriesNumber(equipmentRepairDto.getSeriesNumber());
            }
            if(equipmentRepairDto.getTechnician()!=null ) {
                int id= equipmentRepairDto.getTechnician().getId();
                if(id!=-1){
                    User user = userRepository.findById(equipmentRepairDto.getTechnician().getId())
                            .orElseThrow(() -> new NotFoundException(String.format("Technicien with ID [%d] not found!", equipmentRepairDto.getTechnician().getId())));
                    equipment_repair_existing.setTechnician(user);}
                else{
                    equipment_repair_existing.setIsAccepted(false);
                    equipment_repair_existing.setTechnician(null);
                }
            }
            if(equipmentRepairDto.getClient()!=null ) {
                Client client = clientRepository.findById(equipmentRepairDto.getClient().getId())
                        .orElseThrow(() -> new NotFoundException(String.format("Client with ID [%d] not found!", equipmentRepairDto.getClient().getId())));
                equipment_repair_existing.setClient(client);
            }
            if(equipmentRepairDto.getEntreeBy() != null)
                equipment_repair_existing.setEntreeBy(equipmentRepairDto.getEntreeBy());
            if(equipmentRepairDto.getName() != null)
                equipment_repair_existing.setName(equipmentRepairDto.getName());
            if(equipmentRepairDto.getType()!=null)
                equipment_repair_existing.setType(equipmentRepairDto.getType());
            if(equipmentRepairDto.getEntryDate()!=null)
                equipment_repair_existing.setEntryDate(equipmentRepairDto.getEntryDate());
            equipmentRepository.save(equipment_repair_existing);
            return dtoMapper.toEquipmentDto(equipment_repair_existing);
        }
        else {throw new NotFoundException(String.format("This Equipment [%s] is not found", equipmentRepairDto.getName()));}
    }


    public EquipmentRepairDto deleteAnnonceMaterial(int id) {
        Optional<Equipment_Repair> equipmentRepairOptional=equipmentRepository.findById(id);
        if(equipmentRepairOptional.isPresent()){
            Equipment_Repair equipment_repair_existing=equipmentRepairOptional.get();
            equipmentRepository.delete(equipment_repair_existing);
            return dtoMapper.toEquipmentDto(equipment_repair_existing);
        }
        else{
            throw new NotFoundException(String.format("This Equipment [%s] is not found",id));
        }
    }
    @Transactional
    public Boolean accepteAnnonce(int materielId,String username) {
        Optional<Equipment_Repair> equipmentRepairOptional=equipmentRepository.findById(materielId);
        if(equipmentRepairOptional.isPresent()){
            Equipment_Repair equipmentRepair=equipmentRepairOptional.get();
            equipmentRepair.setIsAccepted(true);
            if(equipmentRepair.getTechnician()==null){
                User user=userRepository.findByUsername(username)
                        .orElseThrow(() -> new NotFoundException(String.format("Technician [%s] not found!",username)));
                equipmentRepair.setTechnician(user);
            }
            equipmentRepository.save(equipmentRepair);
            return true;
        }
        else {
            throw new NotFoundException("This Equipment is not found");
        }
    }

    public List<EquipmentRepairDto> listAnnonceMaterielByTechnician(String username) {
        Optional<User> userOptional =userRepository.findByUsername(username);
        if(userOptional.isPresent()){
            User user=userOptional.get();
            List<Equipment_Repair> equipmentRepairListByTechnician=equipmentRepository.findByTechnicianAndIsAccepted(user,false);
            List<Equipment_Repair>equipmentRepairList=equipmentRepository.findByTechnician(null);
            equipmentRepairListByTechnician.addAll(equipmentRepairList);
            return dtoMapper.toEquipmentsDto(equipmentRepairListByTechnician);
        }
        else{
            throw new NotFoundException("This User is not found");
        }
    }
}
