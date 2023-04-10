package com.growdev.murilo.recados.dto;

import com.growdev.murilo.recados.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecadoDto {
  // private Long id;
  private String assunto;
  private String descricao;
  private String status;
  private Boolean arquivado;
  private String userId;
}
