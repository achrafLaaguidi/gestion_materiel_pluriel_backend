package net.pluriel.gestionApp.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.pluriel.gestionApp.configuration.JwtService;
import net.pluriel.gestionApp.dto.*;
import net.pluriel.gestionApp.exception.ConflictException;
import net.pluriel.gestionApp.exception.NotFoundException;
import net.pluriel.gestionApp.models.*;
import net.pluriel.gestionApp.reposotorie.*;
import net.pluriel.gestionApp.mappers.DtoMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {


    final UserRepository userRepository;
    final DtoMapper dtoMapper;
    final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final UserRepository repository;
    private final EquipmentRepository equipmentRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PermissionRepository permissionRepository;

    @Transactional
    public AuthenticationResponse addUser(UserDto request) {
        Optional<User> userOptional=repository.findByUsername(request.getUsername());
        if(userOptional.isPresent()){
            throw new ConflictException("User is already exist ");
        }
        Role role =dtoMapper.toRole(request.getRole());
        var user = User.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .status(request.getStatus())
                .role(role)
                .build();

        var savedUser = repository.save(user);
       if(role.getUsers()!=null){
           role.getUsers().add(savedUser);}
       else{
           role.setUsers(new ArrayList<>(List.of(savedUser)));
       }
        Role roleSavedU=roleRepository.save(role);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }
    @Transactional
    public UserDto modifyUser(UserDto user){
        Optional<User> userOptional=repository.findById(user.getId());
        if(userOptional.isPresent()){
            User userExisting=userOptional.get();
            if(user.getUsername()!=null ) {
                Optional<User> userOptionalUsername = repository.findByUsername(user.getUsername());
                if (userOptionalUsername.isPresent()&&!userOptionalUsername.get().getId().equals(user.getId())) {
                    throw new ConflictException("The username is already exist !UNIQUE!");
                }
                List<Equipment_Repair> equipmentRepairList=equipmentRepository.findByEntreeByOrderByEntryDateDesc(userExisting.getUsername());
                if(equipmentRepairList!=null){
                    for(Equipment_Repair equipmentRepair:equipmentRepairList){
                        equipmentRepair.setEntreeBy(user.getUsername());
                        equipmentRepository.save(equipmentRepair);
                    }
                }
                userExisting.setUsername(user.getUsername());
            }
            if(user.getFirstName() != null)
                userExisting.setFirstname(user.getFirstName());
            if(user.getLastName()!=null)
                userExisting.setLastname(user.getLastName());
            if(user.getPassword()!=null)
                userExisting.setPassword(passwordEncoder.encode(user.getPassword()));
            if(user.getStatus()!=null)
                userExisting.setStatus(user.getStatus());
            if(user.getRole()!=null) {
                Role roleSaved;
                Optional<Role> roleOptional=roleRepository.findById(user.getRole().getRoleId());
                if(roleOptional.isPresent()) {
                    roleSaved = roleOptional.get();
                    userExisting.setRole(roleSaved);
                    var savedUser = repository.save(userExisting);
                    if (roleSaved.getUsers() != null)
                        roleSaved.getUsers().add(savedUser);
                    else {
                        roleSaved.setUsers(new ArrayList<>(List.of(savedUser)));
                    }
                    roleRepository.save(roleSaved);
                }
            }
            if(user.getRole()!=null||user.getUsername()!=null){
                revokeAllUserTokens(userExisting);
            }


            User savedUser =repository.save(userExisting);

            return dtoMapper.toUserDto(savedUser);
        }
        else {throw new NotFoundException(String.format("This user [%s] is not found",user.getUsername()));}
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        }catch (AuthenticationException e){
            throw new NotFoundException("Username or password is incorrect");
        }

        Optional<User> userOptional=repository.findByUsername(request.getUsername());
        if(userOptional.isPresent()){
            User user=userOptional.get();
            if(user.getStatus().equals(Status.ACTIVE)){
                var jwtToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, jwtToken);
                return AuthenticationResponse.builder()
                        .accessToken(jwtToken)
                        .build();
            }
            else{
                throw new NotFoundException(String.format("This User [%s] is INACTIVE", user.getUsername()));
            }

        }
        else{
            throw new NotFoundException("Username or password is incorrect");
        }

    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Transactional
    public boolean avtivateUser(int id){
        Optional<User> userOptional=userRepository.findById(id);
        if(userOptional.isPresent()) {
            User userExisting = userOptional.get();
            if (userExisting.getStatus() == Status.ACTIVE) {
                userExisting.setStatus(Status.INACTIVE);
                userRepository.save(userExisting);
            } else {
                userExisting.setStatus(Status.ACTIVE);
                userRepository.save(userExisting);
            }
            return true;
        }
        else{ throw new NotFoundException(String.format("This user [%d] is not found",id));}
    }
    @Transactional
    public boolean deleteUser(int id) {
        Optional<User> userOptional=userRepository.findById(id);
        if(userOptional.isPresent()){
            User userExisting=userOptional.get();
            tokenRepository.deleteTokenByUser(userExisting);
            userRepository.delete(userExisting);
            return true;
        }
        else{
            throw new NotFoundException(String.format("This user [%d] is not found",id));
        }
    }
    public List<UserDto> listUser() {
        List<User> userList = userRepository.findAllByOrderByIdDesc();
        return dtoMapper.toUsersDto(userList);
    }
    public List<UserDto> listUserActive(){
        List<User> userList = userRepository.findByStatus(Status.ACTIVE);
        return dtoMapper.toUsersDto(userList);}

    public RoleDto addRole(RoleDto role) {
        Optional<Role> roleOptional=roleRepository.findByRoleName(role.getRoleName());
        if(roleOptional.isEmpty()){
            Role newRole=Role.builder()
                    .roleName(role.getRoleName())
                    .permissions(role.getPermissions())
                    .build();
            Role roleSaved=roleRepository.save(newRole);
            for(Permission permission:roleSaved.getPermissions()){
                Permission permissionFounded=permissionRepository.findById(permission.getId()).orElse(null);
                if(permissionFounded!=null){
                    permissionFounded.getRoles().add(roleSaved);
                    permissionRepository.save(permissionFounded);
                }
            }
            return dtoMapper.toRoleDto(roleSaved);

        }
        else{
            throw new ConflictException("This role is already in use");
        }
    }

    @Transactional
    public boolean modifyRole(RoleDto role) {
        Optional<Role> roleOptional = roleRepository.findById(role.getRoleId());
        if (roleOptional.isPresent()) {
            Role roleExisting = roleOptional.get();
            if (role.getRoleName() != null) {
                Optional<Role> optionalRole = roleRepository.findByRoleName(role.getRoleName());
                if (optionalRole.isPresent()&&optionalRole.get().getRoleId()!=role.getRoleId()) {
                    throw new ConflictException("This role is already exist ");
                }
                roleExisting.setRoleName(role.getRoleName());
            }
            if (role.getPermissions()!=null) {
                List<Permission> updatedPermissions = new ArrayList<>();
                for (Permission permission : role.getPermissions()) {
                    Optional<Permission> permissionOptional = permissionRepository.findById(permission.getId());
                    if (permissionOptional.isPresent()) {
                        Permission permissionExisting = permissionOptional.get();
                        if (!permissionExisting.getRoles().contains(roleExisting)) {
                            permissionExisting.getRoles().add(roleExisting);
                        }
                        updatedPermissions.add(permissionExisting);
                    }
                }
                permissionRepository.saveAll(updatedPermissions);

                for (Permission permission : roleExisting.getPermissions()) {
                    if (!updatedPermissions.contains(permission)) {
                        permission.getRoles().remove(roleExisting);
                        permissionRepository.save(permission);
                    }
                }

                roleExisting.setPermissions(updatedPermissions);
            }
            roleRepository.save(roleExisting);
                return true;
            }
        else {
                throw new NotFoundException(String.format("This role [%d] is not found", role.getRoleId()));
            }
        }

        @Transactional
        public Boolean deleteRole(int id){
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (roleOptional.isPresent()) {
            Role roleExisting = roleOptional.get();
            for (Permission permission : roleExisting.getPermissions()) {
                Optional<Permission> permissionOptional = permissionRepository.findById(permission.getId());
                if (permissionOptional.isPresent()) {
                    Permission permissionExisting = permissionOptional.get();
                    permissionExisting.getRoles().remove(roleExisting);
                    permissionRepository.save(permissionExisting);
                }
            }
            for (User user : roleExisting.getUsers()) {
                Optional<User> userOptional = userRepository.findById(user.getId());
                if (userOptional.isPresent()) {
                    User userExisting = userOptional.get();
                    userExisting.setRole(roleRepository.findByRoleName("USER").orElse(null));
                    userRepository.save(userExisting);
                }
            }
            roleRepository.delete(roleOptional.get());
            return true;
        }else{
            throw new NotFoundException(String.format("This role [%d] is not found", id));
        }
        }
    public List<PermissionDto> listPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        return dtoMapper.toPermissionsDto(permissions);
    }
    public List<RoleDto> listRoles() {
        List<Role> roles = roleRepository.findAll();
        return dtoMapper.toRolesDto(roles);
    }

    public TokenIsRevoked getToken(String tokenName) {
        Token token=tokenRepository.findByToken(tokenName).orElse(null);
        if(token!=null){
            return TokenIsRevoked.builder()
                    .isRevoked(token.revoked)
                    .build();
        }
        return null;
    }
}