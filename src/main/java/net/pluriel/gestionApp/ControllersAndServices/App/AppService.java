package net.pluriel.gestionApp.ControllersAndServices.App;

import lombok.RequiredArgsConstructor;
import net.pluriel.gestionApp.DTO.PermissionDto;
import net.pluriel.gestionApp.DTO.RoleDto;
import net.pluriel.gestionApp.Models.Permission;
import net.pluriel.gestionApp.Models.Role;
import net.pluriel.gestionApp.Reposotories.PermissionRepository;
import net.pluriel.gestionApp.Reposotories.RoleRepository;
import net.pluriel.gestionApp.mappers.DtoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppService {
    private final DtoMapper dtoMapper;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;


    public List<PermissionDto> listPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        return dtoMapper.toPermissionsDto(permissions);
    }
    public List<RoleDto> listRoles() {
        List<Role> roles = roleRepository.findAll();
        return dtoMapper.toRolesDto(roles);
    }
}
