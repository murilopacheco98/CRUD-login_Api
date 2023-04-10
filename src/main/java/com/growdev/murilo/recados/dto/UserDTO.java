package com.growdev.murilo.recados.dto;

import com.growdev.murilo.recados.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private Integer qtdRecadosDesarquivados;
    private Integer qtdRecadosArquivados;
    private String authToken;
    private Boolean enable;
    public UserDTO(User user) {
        id = user.getId();
        email = user.getEmail();
        qtdRecadosArquivados = user.getQtdRecadosArquivados();
        qtdRecadosDesarquivados = user.getQtdRecadosDesarquivados();
        authToken = user.getAuthToken();
        enable = user.getEnable();
    }
}
