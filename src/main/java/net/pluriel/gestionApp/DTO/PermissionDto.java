package net.pluriel.gestionApp.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.pluriel.gestionApp.Models.Role;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto {
    private int id;
    private String name;
    private List<Role> roles;
}
