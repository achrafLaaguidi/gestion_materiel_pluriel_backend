package net.pluriel.gestionApp.ControllersAndServices.Material;

import lombok.RequiredArgsConstructor;
import net.pluriel.gestionApp.DTO.EquipmentRepairDto;
import net.pluriel.gestionApp.Models.Equipment_Repair;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/material")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    @GetMapping("/listMaterialsEntree")
    public List<EquipmentRepairDto> listMaterialsEntree(){
        return materialService.listMaterialsEntree();
    }

    @PostMapping("/addMaterielRepair")
    public EquipmentRepairDto addMaterielRepair(@RequestBody EquipmentRepairDto equipment_repair){
        return materialService.addMaterielRepair(equipment_repair);
    }
    @PutMapping("/modifyMaterialRepair")
    public EquipmentRepairDto modifyMaterialRepair(@RequestBody EquipmentRepairDto equipment_Repair){
        return materialService.modifyMaterialRepair(equipment_Repair);
    }

    @PutMapping("/validateMaterial")
    public boolean validateMaterial(@RequestBody EquipmentRepairDto equipment_repair){
        return materialService.validateMaterial(equipment_repair);
    }

    @GetMapping("/listMaterielAdded/{username}")
    public List<EquipmentRepairDto> listMaterielAdded(@PathVariable String username){
        return materialService.listMaterielAdded(username);
    }



    @GetMapping("/listMaterialsRepairByTechnician/{username}")
    public List<EquipmentRepairDto> listMaterialsRepairByTechnician(@PathVariable String username){
        return materialService.listMaterialsRepairByTechnician(username);
    }
    @GetMapping("/listMaterialsRepairByAll")
    public List<EquipmentRepairDto> listMaterialsRepairByAll(){
        return materialService.listMaterialsRepairByAll();
    }
    @GetMapping("/listMaterialsRepairedByTechnician/{username}")
    public List<EquipmentRepairDto> listMaterialsRepaiedrByTechnician(@PathVariable String username){
        return materialService.listMaterialsRepairedByTechnician(username);
    }
    @GetMapping("/listMaterialsRepairedByAll")
    public List<EquipmentRepairDto> listMaterialsRepairedByAll(){
        return materialService.listMaterialsRepairedByAll();
    }

}
