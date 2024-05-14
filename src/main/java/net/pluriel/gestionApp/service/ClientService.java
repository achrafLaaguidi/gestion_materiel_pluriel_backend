package net.pluriel.gestionApp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.pluriel.gestionApp.dto.ClientDto;
import net.pluriel.gestionApp.dto.EquipmentRepairDto;
import net.pluriel.gestionApp.exception.ConflictException;
import net.pluriel.gestionApp.exception.NotFoundException;
import net.pluriel.gestionApp.models.Client;
import net.pluriel.gestionApp.models.Equipment_Repair;
import net.pluriel.gestionApp.reposotorie.ClientRepository;
import net.pluriel.gestionApp.mappers.DtoMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {


    private final ClientRepository clientRepository;
    private final DtoMapper dtoMapper;

    public ClientDto addClient(ClientDto clientDto) {
        if (clientDto.getDénominationSociale() != null) {
            if (checkClientExists(clientDto.getDénominationSociale())) {
                throw new ConflictException(String.format("This client [%s] is already exist!", clientDto.getDénominationSociale()));
            }
        }
        return  dtoMapper.toClientDto(clientRepository.save(dtoMapper.toClient(clientDto)));
    }

    @Transactional
    public ClientDto modifyClient(ClientDto clientDto) {
        Optional<Client> clientOptional=clientRepository.findById(clientDto.getId());
        if(clientOptional.isPresent()){
            Client clientExisting=clientOptional.get();
            if(clientDto.getDénominationSociale()!=null ) {
                Optional<Client> clientOptionalDE = clientRepository.findByDénominationSociale(clientDto.getDénominationSociale());
                if (clientOptionalDE.isPresent()&&!clientOptionalDE.get().getId().equals(clientDto.getId())) {
                    throw new ConflictException(String.format("This client [%s] is already exist!", clientDto.getDénominationSociale()));
                }
                clientExisting.setDénominationSociale(clientDto.getDénominationSociale());
            }
            if(clientDto.getICE() != null)
                clientExisting.setICE(clientDto.getICE());
            if(clientDto.getSecteurActivité()!=null)
                clientExisting.setSecteurActivité(clientDto.getSecteurActivité());
            if(clientDto.getNatureJuridique()!=null)
                clientExisting.setNatureJuridique(clientDto.getNatureJuridique());
            if(clientDto.getCapitalSocial()!=null)
                clientExisting.setCapitalSocial(clientDto.getCapitalSocial());
            if(clientDto.getDateDeCreation()!=null)
                clientExisting.setDateDeCreation(clientDto.getDateDeCreation());
            if(clientDto.getAdresse()!=null)
                clientExisting.setAdresse(clientDto.getAdresse());
            if(clientDto.getNInscription()!=null)
                clientExisting.setNInscription(clientDto.getNInscription());
            if(clientDto.getVille()!=null){
                clientExisting.setVille(clientDto.getVille());
            }
            if(clientDto.getIdentifiantFiscal()!=null)
                clientExisting.setIdentifiantFiscal(clientDto.getIdentifiantFiscal());
            if(clientDto.getPatente()!=null)
                clientExisting.setPatente(clientDto.getPatente());
            if(clientDto.getNCNSS()!=null)
                clientExisting.setNCNSS(clientDto.getNCNSS());
            if(clientDto.getTelephone()!=null)
                clientExisting.setTelephone(clientDto.getTelephone());
            if(clientDto.getFax()!=null)
                clientExisting.setFax(clientDto.getFax());
            if(clientDto.getEmail()!=null)
                clientExisting.setEmail(clientDto.getEmail());
            if(clientDto.getRepresentantLegalSociete()!=null)
                clientExisting.setRepresentantLegalSociete(clientDto.getRepresentantLegalSociete());
            if(clientDto.getFonction()!=null)
                clientExisting.setFonction(clientDto.getFonction());
            clientRepository.save(clientExisting);
            return dtoMapper.toClientDto(clientExisting);
        }
        else {throw new NotFoundException(String.format("This client [%d] is not found", clientDto.getId()));}
    }

    @Transactional
    public Boolean  deleteClient(int id) {
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
                }
            }
            clientRepository.delete(clientExisting);
            return true;
        }
        else{
            throw new NotFoundException(String.format("This client with ID[%d] is not found",id));
        }
    }

    public List<ClientDto> listClient(){
        List<Client> clients=clientRepository.findAll();
        return dtoMapper.toClientsDto(clients);}

    public boolean addListClient(List<ClientDto> clientDtoList) {
        List<ClientDto> clientDtos=new ArrayList<>();
        for(ClientDto clientDto:clientDtoList){
            if(!checkClientExists(clientDto.getDénominationSociale())){
                clientDtos.add(addClient(clientDto));
            }
        }
        return !clientDtos.isEmpty();
    }
    public boolean checkClientExists(String name) {
        Optional<Client> clientOptional=clientRepository.findByDénominationSociale(name);
        return clientOptional.isPresent();
    }
}
