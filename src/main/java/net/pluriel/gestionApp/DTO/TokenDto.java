package net.pluriel.gestionApp.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.pluriel.gestionApp.Models.TokenType;
import net.pluriel.gestionApp.Models.User;

@Data
@Builder
@AllArgsConstructor
@NamedEntityGraph
public class TokenDto {
    public Integer id;

    public String token;


    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;

    public boolean expired;

}
