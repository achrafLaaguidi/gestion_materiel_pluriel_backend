package net.pluriel.gestionApp.controller;

import lombok.AllArgsConstructor;
import net.pluriel.gestionApp.dto.AuthenticationRequest;
import net.pluriel.gestionApp.dto.AuthenticationResponse;
import net.pluriel.gestionApp.service.UserService;
import net.pluriel.gestionApp.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {


    private final UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @PostMapping("/addUser")
    public ResponseEntity<AuthenticationResponse> addUser(
            @RequestBody UserDto request
    ) {
        return ResponseEntity.ok(userService.addUser(request));
    }

    @PutMapping("/modifyUser")
    public ResponseEntity<UserDto> modifyUser(@RequestBody UserDto user){return ResponseEntity.ok(userService.modifyUser(user));}

    @PutMapping("/activateUser/{id}")
    public boolean activateUser(@PathVariable int id){
        return userService.avtivateUser(id);
    }

    @DeleteMapping("/deleteUser/{id}")
    public boolean deleteUser(@PathVariable int id){
        return userService.deleteUser(id);
    }

    @GetMapping("/listUser")
    public List<UserDto> listUser(){
        return userService.listUser();
    }


    @GetMapping("/listUserActive")
    public List<UserDto> listUserActive(){return userService.listUserActive();}

    @PostMapping("/addRole")
    public RoleDto addRole(@RequestBody RoleDto role){
        return userService.addRole(role);
    }
    @PutMapping("/modifyRole")
    public boolean modifyRole(@RequestBody RoleDto role){
        return userService.modifyRole(role);
    }
    @DeleteMapping("/deleteRole/{id}")
    public boolean deleteRole(@PathVariable int id){
        return userService.deleteRole(id);
    }
    @GetMapping("/listPermissions")
    public List<PermissionDto> listPermissions() {
        return userService.listPermissions();
    }
    @GetMapping("/listRoles")
    public List<RoleDto> listRoles() {
        return userService.listRoles();
    }

}
