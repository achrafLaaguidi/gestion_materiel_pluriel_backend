package net.pluriel.gestionApp.ControllersAndServices.Contract;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.pluriel.gestionApp.DTO.ContractDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contract")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @PostMapping("/addContract/{id}")
    public ContractDto addContract(@RequestBody ContractDto contractDto, @PathVariable Integer id){
        return contractService.addContract(contractDto,id);
    }

    @PutMapping("/modifyContract")
    public ContractDto modifyContract(@Valid @RequestBody ContractDto contractDto){
        return contractService.modifyContract(contractDto);
    }

    @PutMapping("/activateContract/{id}")
    public Boolean activateContract(@PathVariable int id){
        return contractService.avtivateContract(id);
    }

    @DeleteMapping("/deleteContract/{id}")
    public Boolean deleteContract(@PathVariable int id){
        return contractService.deleteContract(id);
    }

    @GetMapping("/listContract")
    public List<ContractDto> listContract(){
        return contractService.listContract();
    }

    @GetMapping("/ContractInfo/{id}")
    public ContractDto ContractInfo(@PathVariable int id){
        return contractService.ContractInfo(id);
    }

}
