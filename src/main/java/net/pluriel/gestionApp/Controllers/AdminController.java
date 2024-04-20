package net.pluriel.gestionApp.Controllers;

import jakarta.validation.Valid;
import net.pluriel.gestionApp.Models.*;
import net.pluriel.gestionApp.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Admin")
public class AdminController {
    final
    AdminService adminService;
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/authenticate")
    public Admin authenticate(@RequestBody Admin admin){
        return adminService.authenticate(admin);
    }

    @PostMapping("/addUser")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user){
        User userAdded=adminService.addUser(user);
        return new ResponseEntity<>(userAdded, HttpStatus.CREATED);
    }

    @PutMapping("/modifyUser")
    public User modifyUser(@Valid @RequestBody User user){return adminService.modifyUser(user);}

    @PutMapping("/activateUser/{id}")
    public User activateUser(@PathVariable int id){
        return adminService.avtivateUser(id);
    }

    @DeleteMapping("/deleteUser/{id}")
    public User deleteUser(@PathVariable int id){
        return adminService.deleteUser(id);
    }

    @GetMapping("/listUser")
    public List<User> listUser(){
        return adminService.listUser();
    }

    @GetMapping("/listUserActive")
    public List<User> listUserActive(){return adminService.listUserActive();}

    @PostMapping("/addClient")
    public ResponseEntity<Client> addClient(@Valid @RequestBody Client client){
        Client clientAdded= adminService.addClient(client);
        return new ResponseEntity<>(clientAdded, HttpStatus.CREATED);
    }

    @PutMapping("/modifyClient")
    public Client modifyClient(@Valid @RequestBody Client client){
        return adminService.modifyClient(client);
    }

    @DeleteMapping("/deleteClient/{id}")
    public Client deleteClient(@PathVariable int id){
        return adminService.deleteClient(id);
    }

    @GetMapping("/listClient")
    public List<Client> listClient(){
        return adminService.listClient();
    }

    @PostMapping("/addContract/{id}")
    public Contract addContract(@Valid @RequestBody Contract contract,@PathVariable Integer id){
        return adminService.addContract(contract,id);
    }

    @PutMapping("/modifyContract")
    public Contract modifyContract(@Valid @RequestBody Contract contract){
        return adminService.modifyContract(contract);
    }

    @PutMapping("/activateContract/{id}")
    public Contract activateContract(@PathVariable int id){
        return adminService.avtivateContract(id);
    }

    @DeleteMapping("/deleteContract/{id}")
    public Contract deleteContract(@PathVariable int id){
        return adminService.deleteContract(id);
    }

    @GetMapping("/listContract")
    public List<Contract> listContract(){
        return adminService.listContract();
    }

    @GetMapping("/ContractInfo/{id}")
    public Contract ContractInfo(@PathVariable int id){
        return adminService.ContractInfo(id);
    }
    @GetMapping("/listMaterielsLivres")
    public List<Equipment_Repair> listMaterielsLivres(){
        return adminService.listMaterielsLivres();
    }
    @PostMapping("/annonceMaterielRepair")
    public Equipment_Repair annonceMaterielRepair(@RequestBody Equipment_Repair equipment_Repair){
        return adminService.annonceMaterielRepair(equipment_Repair);
    }
    @PutMapping("/modifyAnnonceMaterielRepair")
    public Equipment_Repair modifyAnnonceMaterialRepair(@RequestBody Equipment_Repair equipment_Repair){
        return adminService.modifyAnnonceMaterialReapair(equipment_Repair);
    }

    @DeleteMapping("/deleteAnnonceMaterialRepair/{id}")
    public Equipment_Repair deleteAnnonceMaterialRepair(@PathVariable int id){
        return adminService.deleteAnnonceMaterialRepair(id);
    }

    @GetMapping("/listMaterialsRepair")
    public List<Equipment_Repair> listMaterialsRepair(){
        return adminService.listMaterialsRepair();
    }
}

