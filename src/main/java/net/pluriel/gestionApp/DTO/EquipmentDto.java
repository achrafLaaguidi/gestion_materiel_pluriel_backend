package net.pluriel.gestionApp.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.pluriel.gestionApp.Models.Client;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentDto {
    private Integer id;

    private String seriesNumber;
    private String name;

    private Boolean isGarented;


    private String type;


    private Client clientData;

    private String technicianLivreur;

    private TechnicianData technicianData;

    private String entryDate;

    private String releaseDate;

    private String dureeIntervention;

    private String intervention;

    private Boolean isAccepted=false;
}
