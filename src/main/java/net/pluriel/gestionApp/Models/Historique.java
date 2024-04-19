package net.pluriel.gestionApp.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Historique")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Historique {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    @JoinColumn(name = "equipmentrepair_id")
    private Equipment_Repair equipmentRepair;

    @OneToOne
    @JoinColumn(name = "returnSlip_id")
    private Return_Slip returnSlip;
}
