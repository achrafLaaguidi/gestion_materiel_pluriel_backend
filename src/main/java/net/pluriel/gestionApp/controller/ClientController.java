package net.pluriel.gestionApp.controller;

import lombok.RequiredArgsConstructor;
import net.pluriel.gestionApp.service.ClientService;
import net.pluriel.gestionApp.dto.ClientDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    @PostMapping("/addClient")
    public ClientDto addClient(@RequestBody ClientDto clientDto){
        return clientService.addClient(clientDto);
    }
    @PostMapping("/addListClient")
    public List<ClientDto> addListClient(@RequestBody List<ClientDto> clientDtoList){
        return clientService.addListClient( clientDtoList);
    }

    @PutMapping("/modifyClient")
    public ClientDto modifyClient( @RequestBody ClientDto clientDto){
        return clientService.modifyClient(clientDto);
    }

    @DeleteMapping("/deleteClient/{id}")
    public boolean deleteClient(@PathVariable int id){
        return clientService.deleteClient(id);
    }

    @GetMapping("/listClient")
    public List<ClientDto> listClient(){
        return clientService.listClient();
    }

}
