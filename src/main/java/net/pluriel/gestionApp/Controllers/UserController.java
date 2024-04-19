package net.pluriel.gestionApp.Controllers;

import net.pluriel.gestionApp.Models.Equipment_Repair;
import net.pluriel.gestionApp.Models.TechnicianData;
import net.pluriel.gestionApp.Models.User;
import net.pluriel.gestionApp.Services.AdminService;
import net.pluriel.gestionApp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User/{id}")
public class UserController {
    final
    UserService userService;
    private final AdminService adminService;

    @Autowired
    public UserController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }
    @PostMapping("/authenticate")
    public TechnicianData authenticate(@RequestBody User user){
    return userService.authenticate(user);}

    @PostMapping("/addMaterielRepair")
    public Equipment_Repair addMaterielRepair(@RequestBody Equipment_Repair equipment_repair,@PathVariable int id){
        return userService.addMaterielRepair(equipment_repair,id);
    }
    @GetMapping("/listMaterielAdded")
    public List<Equipment_Repair> listMaterielAdded(@PathVariable int id){
        return userService.listMaterielAdded(id);
    }

    @GetMapping("/listMaterielDemandeRepair")
    public List<Equipment_Repair> listMaterielDemandeRepair(@PathVariable int id){
        return userService.listMaterielDemandeRepair(id);
    }

    @PutMapping("/accepteAnnonce/{materielId}")
    public Boolean accepteAnnonce(@PathVariable int materielId,@PathVariable int id){
        return userService.accepteAnnonce(materielId,id);
    }

    @GetMapping("/listMaterielRepair")
    public List<Equipment_Repair> listMaterielRepair(@PathVariable int id){
        return userService.listMaterialsRepair(id);
    }

    @PutMapping("/modifyMaterielRepair")
    public Equipment_Repair modifyMaterielRepair(@RequestBody Equipment_Repair equipment_repair){
        return userService.modifyMaterielRepair(equipment_repair);
    }
}
