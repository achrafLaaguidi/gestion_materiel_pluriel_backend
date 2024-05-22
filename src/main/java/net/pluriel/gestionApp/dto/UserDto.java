package net.pluriel.gestionApp.dto;

import lombok.*;
import net.pluriel.gestionApp.entity.Equipment_Repair;
import net.pluriel.gestionApp.entity.Status;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
