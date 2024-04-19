package net.pluriel.gestionApp.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity
@Table(name = "Return_Slip")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Return_Slip {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private Date date ;
    private String clientName;
    @Column(nullable = false)
    private String designation;
    private Integer quantity;
    private String price;

    @OneToOne
    @JoinColumn(name = "equipmentRepair")
    @JsonIgnore
    private Equipment_Repair equipmentRepair;
}
