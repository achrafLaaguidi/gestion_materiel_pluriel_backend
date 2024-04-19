package net.pluriel.gestionApp.ClassSupplémentaire;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.pluriel.gestionApp.Models.Client;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
public class ClientRequest {
    private Client client;
    private List<String> listEquipment;
    public ClientRequest(){
        this.listEquipment=new ArrayList<>();
    }
}
