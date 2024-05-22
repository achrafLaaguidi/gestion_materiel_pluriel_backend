package net.pluriel.gestionApp.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.pluriel.gestionApp.entity.Permission;
import net.pluriel.gestionApp.entity.User;

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
