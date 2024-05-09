package net.pluriel.gestionApp.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.pluriel.gestionApp.models.TokenType;

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
