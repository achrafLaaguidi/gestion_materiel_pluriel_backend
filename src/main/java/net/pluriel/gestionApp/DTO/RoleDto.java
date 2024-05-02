package net.pluriel.gestionApp.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.pluriel.gestionApp.Models.Permission;
import net.pluriel.gestionApp.Models.User;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDto {
    private int roleId;

    private String roleName;

    private List<User> users;

    private List<Permission> permissions;
}
