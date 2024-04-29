package net.pluriel.gestionApp.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.pluriel.gestionApp.Models.Equipment_Repair;
import net.pluriel.gestionApp.Models.Role;
import net.pluriel.gestionApp.Models.Status;
import net.pluriel.gestionApp.Models.Token;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Status status;
    private List<Equipment_Repair> equipmentRepairList;
    private RoleDto role;
}
