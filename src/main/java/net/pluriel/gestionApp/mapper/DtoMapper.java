package net.pluriel.gestionApp.mapper;

import lombok.RequiredArgsConstructor;
import net.pluriel.gestionApp.dto.*;
import net.pluriel.gestionApp.entity.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DtoMapper {

    private final ModelMapper modelMapper;

    public UserDto toUserDto(User user){
        return modelMapper.map(user, UserDto.class);
    }
    public RoleDto toRoleDto(Role role){
        return modelMapper.map(role, RoleDto.class);
    }
    public Role toRole(RoleDto roleDto){
        return modelMapper.map(roleDto, Role.class);
    }
    public ClientDto toClientDto(Client client){
        return modelMapper.map(client, ClientDto.class);
    }
    public List<ClientDto> toClientsDto(List<Client> clients){
        List<ClientDto> clientDtos = new ArrayList<>();
        for(Client client : clients){
            clientDtos.add(toClientDto(client));
        }
        return clientDtos;
    }
    public Client toClient(ClientDto clientDto) {
        return modelMapper.map(clientDto, Client.class);
    }
    public ContractDto toContractDto(Contract contract){
        return modelMapper.map(contract, ContractDto.class);
    }
    public Contract toContract(ContractDto contractDto){
        return modelMapper.map(contractDto, Contract.class);
    }
    public List<ContractDto> toContractsDto(List<Contract> contracts){
        List<ContractDto> contractDtos = new ArrayList<>();
        for(Contract contract : contracts){
            contractDtos.add(toContractDto(contract));
        }
        return contractDtos;
    }
    public EquipmentRepairDto toEquipmentDto(Equipment_Repair equipment_repair){
        return modelMapper.map(equipment_repair, EquipmentRepairDto.class);
    }
    public EquipmentRepairedDto toEquipmentRepairedDto(Equipment_Repair equipment_repair){
        return modelMapper.map(equipment_repair, EquipmentRepairedDto.class);
    }
    public PermissionDto toPermissionDto(Permission permission){
        return modelMapper.map(permission, PermissionDto.class);
    }
    public TokenDto toTokenDto(Token token){
        return modelMapper.map(token, TokenDto.class);
    }
    public List<UserDto> toUsersDto(List<User> users){
        List<UserDto> userDtos = new ArrayList<>();
        for(User user : users){
            userDtos.add(toUserDto(user));
        }
        return userDtos;
    }
    public List<EquipmentRepairDto> toEquipmentsDto(List<Equipment_Repair> equipmentRepairList){
        List<EquipmentRepairDto> equipmentRepairDtos = new ArrayList<>();
        for(Equipment_Repair equipment_repair : equipmentRepairList){
            equipmentRepairDtos.add(toEquipmentDto(equipment_repair));
        }
        return equipmentRepairDtos;
    }
    public List<EquipmentRepairedDto> toEquipmentsRepairedDto(List<Equipment_Repair> equipmentRepairList){
        List<EquipmentRepairedDto> equipmentRepairDtos = new ArrayList<>();
        for(Equipment_Repair equipment_repair : equipmentRepairList){
            equipmentRepairDtos.add(toEquipmentRepairedDto(equipment_repair));
        }
        return equipmentRepairDtos;
    }

    public List<PermissionDto> toPermissionsDto(List<Permission> permissions){
        List<PermissionDto> permissionDtos = new ArrayList<>();
       for(Permission permission : permissions){
           permissionDtos.add(toPermissionDto(permission));
       }
        return permissionDtos;
    }

    public List<RoleDto> toRolesDto(List<Role> roles) {
        List<RoleDto> roleDtos = new ArrayList<>();
        for(Role role : roles){
            roleDtos.add(toRoleDto(role));
        }
        return roleDtos;
    }

    public User toUser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public List<Permission> toPermissions(List<PermissionDto> permissionDtos) {
        List<Permission> permissions = new ArrayList<>();
        for(PermissionDto permissionDto : permissionDtos){
            permissions.add(toPermission(permissionDto));
        }
        return permissions;
    }

    private Permission toPermission(PermissionDto permissionDto) {
        return modelMapper.map(permissionDto, Permission.class);
    }

    public Equipment_Repair toEquipment(EquipmentRepairDto equipmentRepairDto) {
        return modelMapper.map(equipmentRepairDto, Equipment_Repair.class);
    }
}
