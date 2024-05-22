package net.pluriel.gestionApp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.pluriel.gestionApp.dto.EquipmentRepairDto;

import net.pluriel.gestionApp.exception.ConflictException;
import net.pluriel.gestionApp.exception.NotFoundException;

import net.pluriel.gestionApp.entity.Client;
import net.pluriel.gestionApp.entity.Equipment_Repair;
import net.pluriel.gestionApp.entity.Status;
import net.pluriel.gestionApp.entity.User;
import net.pluriel.gestionApp.repository.ClientRepository;
import net.pluriel.gestionApp.repository.EquipmentRepository;
import net.pluriel.gestionApp.repository.UserRepository;
import net.pluriel.gestionApp.mapper.DtoMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final EquipmentRepository equipmentRepository;
private final ClientRepository clientRepository;
private final UserRepository userRepository;
private final ClientService clientService;
    private final DtoMapper dtoMapper;

    public EquipmentRepairDto modifyMaterialRepair(EquipmentRepairDto equipmentRepairDto){
        Optional<Equipment_Repair> equipmentRepairOptional=equipmentRepository.findById(equipmentRepairDto.getId());
        if(equipmentRepairOptional.isPresent()){
            Equipment_Repair equipment_repair_existing=equipmentRepairOptional.get();

            if(equipmentRepairDto.getSeriesNumber()!=null && !equipmentRepairDto.getSeriesNumber().equals(equipment_repair_existing.getSeriesNumber()) ) {
                if (checkMaterialExist(equipmentRepairDto.getSeriesNumber())) {
                    throw new ConflictException(String.format("This Equipment [%s] already exists!", equipmentRepairDto.getSeriesNumber()));
                }
                equipment_repair_existing.setSeriesNumber(equipmentRepairDto.getSeriesNumber());
            }
            if(equipmentRepairDto.getClient()!=null ) {
                Client client = clientRepository.findById(equipmentRepairDto.getClient().getId())
                        .orElseThrow(() -> new NotFoundException(String.format("Client with ID [%d] not found!", equipmentRepairDto.getClient().getId())));
                equipment_repair_existing.setClient(client);
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
            if(equipmentRepairDto.getEntreeBy() != null)
                equipment_repair_existing.setEntreeBy(equipmentRepairDto.getEntreeBy());
            if(equipmentRepairDto.getName() != null)
                equipment_repair_existing.setName(equipmentRepairDto.getName());
            if(equipmentRepairDto.getType()!=null)
                equipment_repair_existing.setType(equipmentRepairDto.getType());
            if(equipmentRepairDto.getIsGarented()!=null)
                equipment_repair_existing.setIsGarented(equipmentRepairDto.getIsGarented());
            if(equipmentRepairDto.getEntryDate()!=null)
                equipment_repair_existing.setEntryDate(equipmentRepairDto.getEntryDate());
            if(equipmentRepairDto.getReleaseDate()!=null)
                equipment_repair_existing.setReleaseDate(equipmentRepairDto.getReleaseDate());
            if(equipmentRepairDto.getDureeIntervention()!=null)
                equipment_repair_existing.setDureeIntervention(equipmentRepairDto.getDureeIntervention());
            if(equipmentRepairDto.getIntervention()!=null)
                equipment_repair_existing.setIntervention(equipmentRepairDto.getIntervention());
            equipmentRepository.save(equipment_repair_existing);
            return dtoMapper.toEquipmentDto(equipment_repair_existing);
        }
        else {throw new NotFoundException(String.format("This Equipment [%s] is not found", equipmentRepairDto.getName()));}
    }

    public List<EquipmentRepairDto> listMaterialsEntree() {
        List<Equipment_Repair> equipmentRepairList=equipmentRepository.findByIsAcceptedOrderByEntryDateDesc(false);
        return dtoMapper.toEquipmentsDto(equipmentRepairList);
    }

    public Boolean validateMaterial(EquipmentRepairDto equipmentRepairDto) {
        Optional<Equipment_Repair> equipmentRepairOptional=equipmentRepository.findById(equipmentRepairDto.getId());
        if(equipmentRepairOptional.isPresent()){
            Equipment_Repair equipment_repair_existing=equipmentRepairOptional.get();
            if(equipmentRepairDto.getReleaseDate()!=null){
                equipment_repair_existing.setReleaseDate(equipmentRepairDto.getReleaseDate());
                 equipmentRepository.save(equipment_repair_existing);
                 return true;
            }
            else {
                return false;
            }
        }
        else{
            throw new NotFoundException(String.format("This Equipment [%s] is not found", equipmentRepairDto.getName()));
        }
    }

    @Transactional
    public EquipmentRepairDto addMaterielRepair(EquipmentRepairDto equipmentRepairDto) {
        if (checkMaterialExist(equipmentRepairDto.getSeriesNumber())) {
            throw new ConflictException(String.format("This Equipment [%s] already exists!", equipmentRepairDto.getSeriesNumber()));
        }

        if (equipmentRepairDto.getTechnician() != null) {
                Optional<User> technicianOptional = userRepository.findByUsername(equipmentRepairDto.getTechnician().getUsername());
                if (technicianOptional.isPresent()) {
                    User technician = technicianOptional.get();
                    equipmentRepairDto.setTechnician(technician);
                } else {
                    throw new NotFoundException(
                            String.format("Technician [%s] not found!", equipmentRepairDto.getTechnician().getUsername()));
                }
        }
        if(equipmentRepairDto.getClient()!=null) {
            Optional<Client> clientOptional = clientRepository.findByDénominationSociale(equipmentRepairDto.getClient().getDénominationSociale());
            if (clientOptional.isPresent()) {
                Client client = clientOptional.get();
                equipmentRepairDto.setClient(client);
                Equipment_Repair equipment_repair=dtoMapper.toEquipment(equipmentRepairDto);
                equipmentRepository.save(equipment_repair);

            } else {
                throw new NotFoundException(String.format("Client with ID [%d] not found!", equipmentRepairDto.getClient().getId()));
            }

        }
        return equipmentRepairDto;
    }

    public List<EquipmentRepairDto> listMaterielAdded(String username) {

            List<Equipment_Repair> equipmentRepairList = equipmentRepository.findByEntreeByOrderByEntryDateDesc(username);
            return dtoMapper.toEquipmentsDto(equipmentRepairList);

    }






    public List<EquipmentRepairDto> listMaterialsRepairByTechnician(String username) {
        Optional<User> userOptional =userRepository.findByUsername(username);
        if(userOptional.isPresent()){
            User user=userOptional.get();
            List<EquipmentRepairDto> equipmentRepairList=dtoMapper.toEquipmentsDto(equipmentRepository.findByTechnicianAndIsAcceptedOrderByEntryDateDesc(user,true));
            List<EquipmentRepairDto> equipmentRepairDtos=new ArrayList<>();
            for(EquipmentRepairDto equipment:equipmentRepairList){
                if(equipment.getReleaseDate()==null || equipment.getReleaseDate().isEmpty()){
                    equipmentRepairDtos.add(equipment);
                }
            }
            return equipmentRepairDtos;
        }
        else{
            throw new NotFoundException("This User is not found");
        }
    }

    public List<EquipmentRepairDto> listMaterialsRepairByAll() {
        List<Equipment_Repair> equipmentRepairList=new ArrayList<>();
        List<User> userList=userRepository.findByStatus(Status.ACTIVE);
        for(User user:userList){
            equipmentRepairList.addAll(equipmentRepository.findByTechnicianAndIsAcceptedOrderByEntryDateDesc(user,true));
        }
        return dtoMapper.toEquipmentsDto(equipmentRepairList) ;
    }
    public List<EquipmentRepairDto> listMaterialsHistory() {
        List<Equipment_Repair> equipmentRepairList=new ArrayList<>();
        List<User> userList=userRepository.findByStatus(Status.INACTIVE);
        for(User user:userList){
            equipmentRepairList.addAll(equipmentRepository.findByTechnicianAndIsAcceptedOrderByEntryDateDesc(user,true));
        }
        return dtoMapper.toEquipmentsDto(equipmentRepairList) ;
    }


    public List<EquipmentRepairDto> listMaterialsRepairedByTechnician(String username) {
        Optional<User> userOptional =userRepository.findByUsername(username);
        if(userOptional.isPresent()){
            User user=userOptional.get();
            List<EquipmentRepairDto> equipmentRepairList=dtoMapper.toEquipmentsDto(equipmentRepository.findByTechnicianAndIsAcceptedOrderByEntryDateDesc(user,true));
            List<EquipmentRepairDto> equipmentRepairDtos=new ArrayList<>();
        for(EquipmentRepairDto equipment:equipmentRepairList){
            if(equipment.getReleaseDate()!=null && !equipment.getReleaseDate().equals("") ){
                equipmentRepairDtos.add(equipment);
            }
        }
        return equipmentRepairDtos;}
        else{
            throw new NotFoundException("This User is not found");
        }
    }

    public List<EquipmentRepairDto> listMaterialsRepairedByAll() {
        List<EquipmentRepairDto> repairDtoList=listMaterialsRepairByAll();
        List<EquipmentRepairDto> equipmentRepairDtos=new ArrayList<>();
        for(EquipmentRepairDto equipment:repairDtoList){
            if(equipment.getReleaseDate()!=null){
                equipmentRepairDtos.add(equipment);
            }
        }
        return equipmentRepairDtos;
    }

    public List<EquipmentRepairDto> listMaterialsOfToday(String releaseDate) {
        List<Equipment_Repair> repairDtoList=equipmentRepository.findByReleaseDateAndIsAccepted(releaseDate,true);
        return dtoMapper.toEquipmentsDto(repairDtoList);
    }

    @Transactional
    public List<EquipmentRepairDto> addListMaterielRepair(List<EquipmentRepairDto> equipmentRepairDtoList) {
        List<EquipmentRepairDto> repairDtoList=new ArrayList<>();
        for(EquipmentRepairDto equipmentRepairDto:equipmentRepairDtoList){
            equipmentRepairDto.setIsAccepted(true);
            Optional<Client> clientOptional=clientRepository.findByDénominationSociale(equipmentRepairDto.getClient().getDénominationSociale());
            if(!checkMaterialExist(equipmentRepairDto.getSeriesNumber())){
                if(clientOptional.isPresent()){
                    repairDtoList.add(addMaterielRepair(equipmentRepairDto));
                }
                else{
                    clientService.addClient(dtoMapper.toClientDto(equipmentRepairDto.getClient()));
                    repairDtoList.add(addMaterielRepair(equipmentRepairDto));
                }
            }
        }
        return repairDtoList;
    }
    public boolean checkMaterialExist(String seriesNumber) {
        Optional<Equipment_Repair> equipmentRepairOptional=equipmentRepository.findBySeriesNumber(seriesNumber);
        return equipmentRepairOptional.isPresent();
    }
}
