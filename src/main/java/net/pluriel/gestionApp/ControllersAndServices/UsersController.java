package net.pluriel.gestionApp.ControllersAndServices;

import lombok.RequiredArgsConstructor;
import net.pluriel.gestionApp.Models.Equipment_Repair;
import net.pluriel.gestionApp.Services.AdminService;
import net.pluriel.gestionApp.Services.UsersService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User/{username}")
@RequiredArgsConstructor
public class UsersController {
    final
    UsersService usersService;
    private final AdminService adminService;



    @PostMapping("/addMaterielRepair")
    public Equipment_Repair addMaterielRepair(@RequestBody Equipment_Repair equipment_repair,@PathVariable int id){
        return usersService.addMaterielRepair(equipment_repair,id);
    }
    @GetMapping("/listMaterielAdded")
    public List<Equipment_Repair> listMaterielAdded(@PathVariable String username){
        return usersService.listMaterielAdded(username);
    }

    @GetMapping("/listMaterielDemandeRepair")
    public List<Equipment_Repair> listMaterielDemandeRepair(@PathVariable String username){
        return usersService.listMaterielDemandeRepair(username);
    }

    @PutMapping("/accepteAnnonce/{materielId}")
    public Boolean accepteAnnonce(@PathVariable int materielId,@PathVariable String username){
        return usersService.accepteAnnonce(materielId,username);
    }

    @GetMapping("/listMaterielRepair")
    public List<Equipment_Repair> listMaterielRepair(@PathVariable String username){
        return usersService.listMaterialsRepair(username);
    }

    @PutMapping("/modifyMaterielRepair")
    public Equipment_Repair modifyMaterielRepair(@RequestBody Equipment_Repair equipment_repair){
        return usersService.modifyMaterielRepair(equipment_repair);
    }
}
