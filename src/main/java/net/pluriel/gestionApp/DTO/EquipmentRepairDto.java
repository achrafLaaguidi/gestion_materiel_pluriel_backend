package net.pluriel.gestionApp.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.pluriel.gestionApp.Models.Client;
import net.pluriel.gestionApp.Models.User;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentRepairDto {
    private Integer id;

    private String seriesNumber;
    private String name;

    private Boolean isGarented;


    private String type;


    private Client client;

    private User technician;


    private String entreeBy;


    private String entryDate;

    private String releaseDate;

    private String dureeIntervention;

    private String intervention;

    private Boolean isAccepted=false;
}
