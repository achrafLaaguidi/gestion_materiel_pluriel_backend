package net.pluriel.gestionApp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.pluriel.gestionApp.Models.Client;
import net.pluriel.gestionApp.Models.ContractType;
import net.pluriel.gestionApp.Models.Status;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractDto {
    private Integer id;
    private String codeContract;
    private ContractType title;
    private Status status=Status.INACTIVE;

    private Client client;

}
