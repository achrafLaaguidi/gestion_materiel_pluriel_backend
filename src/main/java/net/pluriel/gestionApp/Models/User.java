package net.pluriel.gestionApp.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Entity
@Table(name = "User")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Integer id ;
    @Size(max = 20,message = "The first name is longer")
    private String firstName;
    @Size(max = 20,message = "The last name is longer")
    private String lastName;


    @Column(nullable = false)
    @Size(max = 20,message = "The username is longer or shorter")
    private String username;

    @Column(nullable = false)
    private String password;
    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "technician")
    @JsonIgnore
    private List<Equipment_Repair> equipmentRepairList;


}