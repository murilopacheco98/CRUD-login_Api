package com.growdev.murilo.recados.dto;

import com.growdev.murilo.recados.entities.Recado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecadoDto {
  private Long id;
  private String assunto;
  private String descricao;
  private String status;
  private Boolean arquivado;
  private Long userId;

  public RecadoDto(Recado recado) {
    id = recado.getId();
    assunto = recado.getAssunto();
    descricao = recado.getDescricao();
    status = recado.getStatus();
    arquivado = recado.getArquivado();
    userId = recado.getUser().getId();
  }
}
