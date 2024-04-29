package net.pluriel.gestionApp.ControllersAndServices.user;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.pluriel.gestionApp.Configuration.config.JwtService;
import net.pluriel.gestionApp.DTO.UserDto;
import net.pluriel.gestionApp.Errors.ConflictException;
import net.pluriel.gestionApp.Errors.NotFoundException;
import net.pluriel.gestionApp.Models.*;
import net.pluriel.gestionApp.Reposotories.RoleRepository;
import net.pluriel.gestionApp.Reposotories.TokenRepository;
import net.pluriel.gestionApp.Reposotories.UserRepository;
import net.pluriel.gestionApp.mappers.DtoMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse addUser(UserDto request) {
        Optional<User> userOptional=repository.findByUsername(request.getUsername());
        if(userOptional.isPresent()){
            throw new ConflictException("User is already exist ");
        }
        Role role=dtoMapper.toRole(request.getRole());
        Role roleSaved=roleRepository.save(role);
        var user = User.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .status(request.getStatus())
                .role(roleSaved)
                .build();

        var savedUser = repository.save(user);
        role.getUser().add(savedUser);
        roleRepository.save(roleSaved);
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
                userExisting.setRole(dtoMapper.toRole(user.getRole()));
                Role role=roleRepository.findById(userExisting.getRole().getRoleId()).orElse(null);
                assert role != null;
                role.setRoleName(user.getRole().getRoleName());
                role.setPermissions(user.getRole().getPermissions());
                roleRepository.save(role);
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

}
