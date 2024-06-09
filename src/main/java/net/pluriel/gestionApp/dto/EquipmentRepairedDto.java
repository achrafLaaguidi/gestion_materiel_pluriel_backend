package net.pluriel.gestionApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.pluriel.gestionApp.entity.User;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentRepairedDto {
    private Integer id;

    private String seriesNumber;
    private String name;

    private Boolean isGarented;


    private String type;


    private ClientDto client;


    private User technician;


    private String entreeBy;


    private String entryDate;

    private String releaseDate;

    private String dureeIntervention;

    private String intervention;

}
