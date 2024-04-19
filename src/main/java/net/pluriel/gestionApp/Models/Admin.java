package net.pluriel.gestionApp.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.boot.convert.DataSizeUnit;

@Entity
@Table(name = "Admin")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id
    @GeneratedValue
    private Integer id ;

    @Column(nullable = false)
    @Size(min = 5,max = 10)
    private String username;

    @Column(nullable = false)
    private String password;

}
