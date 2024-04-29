package net.pluriel.gestionApp.ControllersAndServices.App;

import lombok.RequiredArgsConstructor;
import net.pluriel.gestionApp.DTO.PermissionDto;
import net.pluriel.gestionApp.DTO.RoleDto;
import net.pluriel.gestionApp.Models.*;
import net.pluriel.gestionApp.Services.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/service")
@RequiredArgsConstructor
public class AppController {
    final
    AdminService adminService;

    final
    AppService appService;

    @GetMapping("/listPermissions")
    public List<PermissionDto> listPermissions() {
        return appService.listPermissions();
    }
    @GetMapping("/listRoles")
    public List<RoleDto> listRoles() {
        return appService.listRoles();
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

    @PutMapping("/modifyMaterialRepair")
    public Equipment_Repair modifyMaterialRepair(@RequestBody Equipment_Repair equipment_Repair){
        return adminService.modifyMaterialRepair(equipment_Repair);
    }
    @PutMapping("/validateMaterial")
    public Equipment_Repair validateMaterial(@RequestBody Equipment_Repair equipment_repair){
        return adminService.validateMaterial(equipment_repair);
    }
}

