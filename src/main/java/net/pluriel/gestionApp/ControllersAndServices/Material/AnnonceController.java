package net.pluriel.gestionApp.ControllersAndServices.Material;

import lombok.RequiredArgsConstructor;
import net.pluriel.gestionApp.DTO.EquipmentRepairDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/annonce")
@RequiredArgsConstructor
public class AnnonceController {
    final
    AnnonceService annonceService;
    @PostMapping("/annonceMaterielRepair")
    public EquipmentRepairDto addAnnonceMaterial(@RequestBody EquipmentRepairDto equipment_Repair){
        return annonceService.addAnnonceMaterial(equipment_Repair);
    }
    @PutMapping("/modifyAnnonceMaterielRepair")
    public EquipmentRepairDto modifyAnnonceMaterial(@RequestBody EquipmentRepairDto equipment_Repair){
        return annonceService.modifyAnnonceMaterial(equipment_Repair);
    }

    @DeleteMapping("/deleteAnnonceMaterialRepair/{id}")
    public EquipmentRepairDto deleteAnnonceMaterial(@PathVariable int id){
        return annonceService.deleteAnnonceMaterial(id);
    }
    @PutMapping("/accepteAnnonce/{materielId}/{username}")
    public Boolean accepteAnnonce(@PathVariable int materielId,@PathVariable String username){
        return annonceService.accepteAnnonce(materielId,username);
    }
    @GetMapping("/listAnnonceMaterielByTechnician/{username}")
    public List<EquipmentRepairDto> listAnnonceMaterielByTechnician(@PathVariable String username){
        return annonceService.listAnnonceMaterielByTechnician(username);
    }
}
