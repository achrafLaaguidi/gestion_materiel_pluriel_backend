package net.pluriel.gestionApp.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.pluriel.gestionApp.Models.Contract;
import net.pluriel.gestionApp.Models.Equipment_Repair;
import net.pluriel.gestionApp.Models.FonctionSociete;
import net.pluriel.gestionApp.Models.NatureJuridique;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Client")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue
    private Integer id;

    private String dénominationSociale;

    @Column(nullable = false)
    private String secteurActivité;

    @Enumerated(EnumType.STRING)
    private NatureJuridique natureJuridique;

    @DecimalMin(value = "0.0", message = " Its cannot be negative")
    private Double capitalSocial;

    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private Date dateDeCreation;

    private String adresse;

    @Column(name = "nInscription")
    private String nInscription;

    private String ville;


    @DecimalMin(value = "0.0", message = " Its cannot be negative")
    private Integer identifiantFiscal;

    private String patente;

    @DecimalMin(value = "0.0", message = " Its cannot be negative")
    private Integer NCNSS;

    @DecimalMin(value = "0.0", message = " Its cannot be negative")
    private Integer ICE;

    @Pattern(regexp = "\\d{12}",message = "You need to enter a valid numberPhone")
    private String telephone;

    @Pattern(regexp = "\\d{12}",message = "You need to enter a valid numberPhone (fax)")
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

    @Transient
    private Object contractData;

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private List<Equipment_Repair> equipmentRepairList;

}

