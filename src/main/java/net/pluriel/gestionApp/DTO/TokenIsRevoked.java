package net.pluriel.gestionApp.DTO;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenIsRevoked {
    private Boolean isRevoked;
}

