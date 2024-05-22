package net.pluriel.gestionApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.pluriel.gestionApp.entity.Contract;
import net.pluriel.gestionApp.entity.Equipment_Repair;
import net.pluriel.gestionApp.entity.FonctionSociete;
import net.pluriel.gestionApp.entity.NatureJuridique;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDto {


    private Integer id;

    private String dénominationSociale;

    private String secteurActivité;

    private NatureJuridique natureJuridique;

    private Double capitalSocial;

    private String dateDeCreation;

    private String adresse;

    private String nInscription;

    private String ville;


    private Integer identifiantFiscal;

    private String patente;

    private Integer NCNSS;

    private Integer ICE;

    private String telephone;

    private String fax;

    private String email;

    private String representantLegalSociete;

    private FonctionSociete fonction;

    private Contract contract;

    private List<Equipment_Repair> equipmentRepairList;

}
