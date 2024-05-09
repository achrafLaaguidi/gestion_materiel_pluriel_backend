package net.pluriel.gestionApp.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenIsRevoked {
    private Boolean isRevoked;
}

