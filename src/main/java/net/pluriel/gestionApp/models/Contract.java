package net.pluriel.gestionApp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contract {
@Id
@GeneratedValue
private Integer id;
@Size(max=10,message = "the code is longer!!")
private String codeContract;
@Enumerated(EnumType.STRING)
@Column(nullable = false)
private ContractType title;
@Column(nullable = false)
private Status status=Status.INACTIVE;

@OneToOne
@JoinColumn(name = "client_id")
@JsonIgnore
private Client client;

}
