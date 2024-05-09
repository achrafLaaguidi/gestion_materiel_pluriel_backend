package net.pluriel.gestionApp.service;

import lombok.RequiredArgsConstructor;
import net.pluriel.gestionApp.dto.ContractDto;
import net.pluriel.gestionApp.exception.ConflictException;
import net.pluriel.gestionApp.exception.NotFoundException;
import net.pluriel.gestionApp.models.Client;
import net.pluriel.gestionApp.models.Contract;
import net.pluriel.gestionApp.models.Status;
import net.pluriel.gestionApp.reposotorie.ClientRepository;
import net.pluriel.gestionApp.reposotorie.ContractRepository;
import net.pluriel.gestionApp.mappers.DtoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final ClientRepository clientRepository;
    private final DtoMapper dtoMapper;

    public ContractDto addContract(ContractDto contractDto, int id){
        Optional<Contract> contractOptional =contractRepository.findByCodeContract(contractDto.getCodeContract());
        if(contractOptional.isPresent()){
            throw new ConflictException(String.format("This contract [%s] is already exist!",contractDto.getCodeContract()));
        }
        else{
            Optional<Client>clientOptional=clientRepository.findById(id);
            if(clientOptional.isPresent()){
                Client client=clientOptional.get();
                contractDto.setClient(client);
                Contract contract=contractRepository.save(dtoMapper.toContract(contractDto));
                client.setContract(contract);
                clientRepository.save(client);
            }
            else{
                throw new NotFoundException("The Client is not found");
            }
            return contractDto;
        }
    }

    public ContractDto modifyContract(ContractDto contractDto) {
        Optional<Contract> ContractOptional=contractRepository.findById(contractDto.getId());
        if(ContractOptional.isPresent()){
            Contract contractExisting=ContractOptional.get();
            if(contractDto.getCodeContract()!=null ) {
                Optional<Contract> ContractOptionalCode = contractRepository.findByCodeContract(contractDto.getCodeContract());
                if (ContractOptionalCode.isPresent() && !ContractOptional.get().getCodeContract().equals(contractDto.getCodeContract())) {
                    throw new ConflictException(String.format("This contract [%s] is already exist!",contractDto.getCodeContract()));
                }
                contractExisting.setCodeContract(contractDto.getCodeContract());
            }
            if(contractDto.getTitle() != null)
                contractExisting.setTitle(contractDto.getTitle());
            if(contractDto.getStatus()!=null)
                contractExisting.setStatus(contractDto.getStatus());
            Contract contract= contractRepository.save(contractExisting);
            return dtoMapper.toContractDto(contract);
        }
        else {throw new NotFoundException(String.format("This contract [%s] is not found",contractDto.getCodeContract()));}
    }
    public Boolean avtivateContract(int id){
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
            return true;
        }
        else{ throw new NotFoundException(String.format("This contract [%d] is not found",id));}
    }
    public Boolean deleteContract(int id) {
        Optional<Contract> contractOptional=contractRepository.findById(id);
        if(contractOptional.isPresent()){
            Contract contractExisting=contractOptional.get();
            if(contractExisting.getClient()!=null){
                contractExisting.getClient().setContract(null);
                contractExisting.setClient(null);
            }
            contractRepository.delete(contractExisting);
            return true;
        }
        else{
            throw new NotFoundException(String.format("This contract with ID [%d] is not found",id));
        }
    }
    public List<ContractDto> listContract() {
        List<Contract> contracts=contractRepository.findAll();

        return dtoMapper.toContractsDto(contracts);
    }

    public ContractDto ContractInfo(int id) {
        Optional<Contract> contractOptional=contractRepository.findById(id);
        if(contractOptional.isPresent()) {
            Contract contract=contractOptional.get();
            return dtoMapper.toContractDto(contract);
        }
        return null;
    }
}
