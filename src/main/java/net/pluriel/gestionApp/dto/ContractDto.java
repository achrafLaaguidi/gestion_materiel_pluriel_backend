package net.pluriel.gestionApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.pluriel.gestionApp.entity.Client;
import net.pluriel.gestionApp.entity.ContractType;
import net.pluriel.gestionApp.entity.Status;

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
