package net.pluriel.gestionApp.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.pluriel.gestionApp.Models.Contract;
import net.pluriel.gestionApp.Models.Equipment_Repair;
import net.pluriel.gestionApp.Models.FonctionSociete;
import net.pluriel.gestionApp.Models.NatureJuridique;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
