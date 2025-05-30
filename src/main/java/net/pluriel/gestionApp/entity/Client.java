package net.pluriel.gestionApp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Client")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String dénominationSociale;


    private String secteurActivité;

    @Enumerated(EnumType.STRING)
    private NatureJuridique natureJuridique;


    private Double capitalSocial;

    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private String dateDeCreation;

    private String adresse;

    @Column(name = "nInscription")
    private String nInscription;

    private String ville;


    private Integer identifiantFiscal;

    private String patente;

    private Integer NCNSS;

    private Integer ICE;

    private String telephone;

    private String fax;

    @Email(message = "You need to enter a valid email")
    private String email;

    private String representantLegalSociete;

    @Enumerated(EnumType.STRING)
    private FonctionSociete fonction;

    @OneToOne
    @JoinColumn(name = "contract_id")
    @JsonIgnore
    private Contract contract;

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private List<Equipment_Repair> equipmentRepairList;

}

