package net.pluriel.gestionApp.ControllersAndServices.user;

import lombok.AllArgsConstructor;
import net.pluriel.gestionApp.DTO.UserDto;
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

}
