package net.pluriel.gestionApp.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.pluriel.gestionApp.DTO.TechnicianData;

@Entity
@Table(name = "Equipment_Repair")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Equipment_Repair {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String seriesNumber;
    @Column(nullable = false)
    private String name;

    private Boolean isGarented;


    private String type;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private Client client;


    @ManyToOne
    @JoinColumn(name = "technician_id")
    @JsonIgnore
    private User technician;


    private String entreeBy;


    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private String entryDate;

    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private String releaseDate;

    private String dureeIntervention;

    private String intervention;

    private Boolean isAccepted=false;
}
