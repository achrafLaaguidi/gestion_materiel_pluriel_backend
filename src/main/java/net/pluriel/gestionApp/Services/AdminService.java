package net.pluriel.gestionApp.Services;

import jakarta.transaction.Transactional;
import net.pluriel.gestionApp.Errors.*;
import net.pluriel.gestionApp.Models.*;
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


    public Admin authenticate(Admin admin) {
        Optional<Admin> adminOptional = repository.findByUsernameAndPassword(admin.getUsername(), admin.getPassword());
        if (adminOptional.isPresent()) {
            return adminOptional.get();
        } else {
            throw new NotFoundException(String.format("This Admin [%s] is not found", admin.getUsername()));
        }
    }

    public User addUser(User user) {
        Optional<User> userOptional=userRepository.findByUsername(user.getUsername());
        if(userOptional.isPresent()){
            throw new ConflictException("User is already exist !UNIQUE!");
        }
        userRepository.save(user);
        return user;

    }
    public User modifyUser(User user){
        Optional<User> userOptional=userRepository.findById(user.getId());
        if(userOptional.isPresent()){
            User userExisting=userOptional.get();
            if(user.getUsername()!=null ) {
                Optional<User> userOptionalUsername = userRepository.findByUsername(user.getUsername());
                if (userOptionalUsername.isPresent()&&!userOptionalUsername.get().getId().equals(user.getId())) {
                    throw new ConflictException("The username is already exist !UNIQUE!");
                }
                userExisting.setUsername(user.getUsername());
            }
            if(user.getFirstName() != null)
                userExisting.setFirstName(user.getFirstName());
            if(user.getLastName()!=null)
                userExisting.setLastName(user.getLastName());
            if(user.getPassword()!=null)
                userExisting.setPassword(user.getPassword());
            if(user.getStatus()!=null)
                userExisting.setStatus(user.getStatus());
            userRepository.save(userExisting);
            return userExisting;
        }
        else {throw new NotFoundException(String.format("This user [%s] is not found",user.getUsername()));}
    }
    public User avtivateUser(int id){
        Optional<User> userOptional=userRepository.findById(id);
        if(userOptional.isPresent()) {
            User userExisting = userOptional.get();
            if (userExisting.getStatus() == Status.ACTIVE) {
                userExisting.setStatus(Status.INACTIVE);
                userRepository.save(userExisting);
            } else {
                userExisting.setStatus(Status.ACTIVE);
                userRepository.save(userExisting);
            }
            return userExisting;
        }
       else{ throw new NotFoundException(String.format("This user [%d] is not found",id));}
    }
    public User deleteUser(int id) {
        Optional<User> userOptional=userRepository.findById(id);
        if(userOptional.isPresent()){
            User userExisting=userOptional.get();
            userRepository.delete(userExisting);
            return userExisting;
        }
        else{
            throw new NotFoundException(String.format("This user [%d] is not found",id));
        }
    }

    public Client addClient(Client client) {
        if (client.getICE() != null) {
            Optional<Client> clientOptional = clientRepository.findByICE(client.getICE());
            if (clientOptional.isPresent()) {
                throw new ConflictException(String.format("This client [%d] is already exist!", client.getICE()));
            }
        }
        clientRepository.save(client);
        return client;
    }

    public Client modifyClient(Client client) {
        Optional<Client> clientOptional=clientRepository.findById(client.getId());
        if(clientOptional.isPresent()){
            Client clientExisting=clientOptional.get();
            if(client.getICE()!=null ) {
                Optional<Client> clientOptionalICE = clientRepository.findByICE(client.getICE());
                if (clientOptionalICE.isPresent()&&!clientOptionalICE.get().getId().equals(client.getId())) {
                    throw new ConflictException(String.format("This client [%d] is already exist!", client.getICE()));
                }
                clientExisting.setICE(client.getICE());
            }
            if(client.getDénominationSociale() != null)
                clientExisting.setDénominationSociale(client.getDénominationSociale());
            if(client.getSecteurActivité()!=null)
                clientExisting.setSecteurActivité(client.getSecteurActivité());
            if(client.getNatureJuridique()!=null)
                clientExisting.setNatureJuridique(client.getNatureJuridique());
            if(client.getCapitalSocial()!=null)
                clientExisting.setCapitalSocial(client.getCapitalSocial());
            if(client.getDateDeCreation()!=null)
                clientExisting.setDateDeCreation(client.getDateDeCreation());
            if(client.getAdresse()!=null)
                clientExisting.setAdresse(client.getAdresse());
            if(client.getNInscription()!=null)
                clientExisting.setNInscription(client.getNInscription());
            if(client.getVille()!=null){
                clientExisting.setVille(client.getVille());
            }
            if(client.getIdentifiantFiscal()!=null)
                clientExisting.setIdentifiantFiscal(client.getIdentifiantFiscal());
            if(client.getPatente()!=null)
                clientExisting.setPatente(client.getPatente());
            if(client.getNCNSS()!=null)
                clientExisting.setNCNSS(client.getNCNSS());
            if(client.getTelephone()!=null)
                clientExisting.setTelephone(client.getTelephone());
            if(client.getFax()!=null)
                clientExisting.setFax(client.getFax());
            if(client.getEmail()!=null)
                clientExisting.setEmail(client.getEmail());
            if(client.getRepresentantLegalSociete()!=null)
                clientExisting.setRepresentantLegalSociete(client.getRepresentantLegalSociete());
            if(client.getFonction()!=null)
                clientExisting.setFonction(client.getFonction());
            clientRepository.save(clientExisting);
            return clientExisting;
        }
        else {throw new NotFoundException(String.format("This client [%d] is not found",client.getId()));}
    }

    public Client  deleteClient(int id) {
        Optional<Client> clientOptional=clientRepository.findById(id);
        if(clientOptional.isPresent()){
            Client clientExisting=clientOptional.get();
            if(clientExisting.getContract()!=null){
                clientExisting.getContract().setClient(null);
                clientExisting.setContract(null);
            }
            for(Equipment_Repair er:clientExisting.getEquipmentRepairList()){
                if(er.getClient()==clientExisting){
                    er.setClient(null);
                    er=null;
                }
            }
            clientRepository.delete(clientExisting);
            return clientExisting;
        }
        else{
            throw new NotFoundException(String.format("This client with ID[%d] is not found",id));
        }
    }

    public Contract addContract(Contract contract,int id){
        Optional<Contract> contractOptional =contractRepository.findByCodeContract(contract.getCodeContract());
        if(contractOptional.isPresent()){
            throw new ConflictException(String.format("This contract [%s] is already exist!",contract.getCodeContract()));
        }
        else{
            Optional<Client>clientOptional=clientRepository.findById(id);
            if(clientOptional.isPresent()){
                Client client=clientOptional.get();
                contract.setClient(client);
                contractRepository.save(contract);
                client.setContract(contract);
                clientRepository.save(client);
            }
            else{
                throw new NotFoundException("The Client is not found");
            }
            return contract;
        }
    }

    public Contract modifyContract(Contract contract) {
        Optional<Contract> ContractOptional=contractRepository.findById(contract.getId());
        if(ContractOptional.isPresent()){
            Contract contractExisting=ContractOptional.get();
            if(contract.getCodeContract()!=null ) {
                Optional<Contract> ContractOptionalCode = contractRepository.findByCodeContract(contract.getCodeContract());
                if (ContractOptionalCode.isPresent() && !ContractOptional.get().getCodeContract().equals(contract.getCodeContract())) {
                    throw new ConflictException(String.format("This contract [%s] is already exist!",contract.getCodeContract()));
                }
                contractExisting.setCodeContract(contract.getCodeContract());
            }
            if(contract.getTitle() != null)
                contractExisting.setTitle(contract.getTitle());
            if(contract.getStatus()!=null)
                contractExisting.setStatus(contract.getStatus());
            contractRepository.save(contractExisting);
            return contractExisting;
        }
        else {throw new NotFoundException(String.format("This contract [%s] is not found",contract.getCodeContract()));}
    }
    public Contract avtivateContract(int id){
        Optional<Contract> contractOptional=contractRepository.findById(id);
        if(contractOptional.isPresent()) {
            Contract contractExisting = contractOptional.get();
            if (contractExisting.getStatus() == Status.ACTIVE) {
                contractExisting.setStatus(Status.INACTIVE);
                contractRepository.save(contractExisting);
            } else {
                contractExisting.setStatus(Status.ACTIVE);
                contractRepository.save(contractExisting);
            }
            return contractExisting;
        }
        else{ throw new NotFoundException(String.format("This contract [%d] is not found",id));}
    }
    public Contract deleteContract(int id) {
        Optional<Contract> contractOptional=contractRepository.findById(id);
        if(contractOptional.isPresent()){
            Contract contractExisting=contractOptional.get();
            if(contractExisting.getClient()!=null){
                contractExisting.getClient().setContract(null);
                contractExisting.setClient(null);
            }
            contractRepository.delete(contractExisting);
            return contractExisting;
        }
        else{
            throw new NotFoundException(String.format("This contract with ID [%d] is not found",id));
        }
    }

    public List<User> listUser() {
        return userRepository.findAll();
    }
    public List<User> listUserActive(){return userRepository.findByStatus(Status.ACTIVE);}

    public List<Client> listClient(){
        List<Client> clients=clientRepository.findAll();
        for (Client client : clients) {
            Contract contract = client.getContract();
            Object contractData = (contract != null) ? contract : null;
            client.setContractData(contractData);
        }
        return clients;}

    public List<Contract> listContract() {
        List<Contract> contracts=contractRepository.findAll();
        for (Contract contract : contracts) {
            Client client = contract.getClient();
            Object clientData = (client != null) ? client : null;
            contract.setClientData(clientData);
        }
        return contracts;
    }

    public Contract ContractInfo(int id) {
        Optional<Contract> contractOptional=contractRepository.findById(id);
        if(contractOptional.isPresent()) {
            Contract contract=contractOptional.get();
            Client client = contract.getClient();
            Object clientData = (client != null) ? client : null;
            contract.setClientData(clientData);
            return contract;
        }
        return null;
    }

    public Equipment_Repair annonceMaterielRepair(Equipment_Repair equipmentRepair) {
        if (equipmentRepository.findBySeriesNumber(equipmentRepair.getSeriesNumber()).isPresent()) {
            throw new ConflictException(String.format("This Equipment [%s] already exists!", equipmentRepair.getSeriesNumber()));
        }
    if(equipmentRepair.getClientData()!=null) {
        Optional<Client> clientOptional = clientRepository.findById(equipmentRepair.getClientData().getId());
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            equipmentRepair.setClient(client);
            equipmentRepair.setClientData(client);

            if (equipmentRepair.getTechnicianData() != null) {
                Optional<User> technicianOptional = userRepository.findByUsername(equipmentRepair.getTechnicianData().getUsername());
                if (technicianOptional.isPresent()) {
                    User technician = technicianOptional.get();

                    equipmentRepair.setTechnician(technician);
                    equipmentRepair.setTechnicianData(new TechnicianData(technician.getId(), technician.getUsername()));

                    technician.getEquipmentRepairList().add(equipmentRepair);
                    userRepository.save(technician);
                } else {
                    throw new NotFoundException(
                            String.format("Technician [%s] not found!", equipmentRepair.getTechnicianData().getUsername()));
                }
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
        return equipmentRepository.findByIsAccepted(true);
    }
}
