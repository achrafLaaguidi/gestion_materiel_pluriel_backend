package net.pluriel.gestionApp.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Permission {
    @Id
    private int id;

    private String name;

    @ManyToMany
    @JsonIgnore
    private List<Role> roles;
}
