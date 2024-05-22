package net.pluriel.gestionApp.dto;


import lombok.*;
import net.pluriel.gestionApp.entity.Client;
import net.pluriel.gestionApp.entity.User;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
