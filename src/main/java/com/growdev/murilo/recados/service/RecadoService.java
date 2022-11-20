package com.growdev.murilo.recados.service;

import com.growdev.murilo.recados.dto.RecadoDto;
import com.growdev.murilo.recados.entities.Recado;
import com.growdev.murilo.recados.exceptions.customExceptions.BadRequestException;
import com.growdev.murilo.recados.exceptions.customExceptions.InternalServerErrorException;
import com.growdev.murilo.recados.exceptions.customExceptions.NotFoundException;
import com.growdev.murilo.recados.repository.RecadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class RecadoService {
  @Autowired
  RecadoRepository recadoRepository;

  public void save(RecadoDto recadoDto) {
    if (recadoDto.getAssunto() == null) {
      throw new BadRequestException("Todos os campos devem estar preenchidos");
    }
    if (recadoDto.getDescricao() == null) {
      throw new BadRequestException("Todos os campos devem estar preenchidos");
    }

    Recado recado = new Recado(recadoDto.getAssunto(), recadoDto.getDescricao(), recadoDto.getStatus());
    try {
      recadoRepository.save(recado);
    } catch (Exception e) {
      throw new InternalServerErrorException("Não é possível salvar o recado.");
    }
  }

  public List<Recado> getAll() {
    try {
      return recadoRepository.findAll();
    } catch (Exception e) {
      throw new InternalServerErrorException("Problema interno na requisição dos recados.");
    }
  }

  public Recado findById(Long id) {
    Recado recado = recadoRepository.findById(id).orElseThrow(() -> new NotFoundException("Recado não encontrado"));
    return recado;
  }

  public void update(Long id, RecadoDto recadoDto) {
    Recado recado = recadoRepository.findById(id).orElseThrow(() -> new NotFoundException("Recado não encontrado"));
    recado.setDescricao(recadoDto.getDescricao());
    recado.setAssunto(recadoDto.getAssunto());
    recado.setStatus(recadoDto.getStatus());
    recado.setArquivado(recadoDto.getArquivado());
    try {
      recadoRepository.save(recado);
    } catch (Exception e) {
      throw new InternalServerErrorException("Não é possível atualizar o recado.");
    }
  }

  public void delete(Long id) {
    try {
      recadoRepository.deleteById(id);
    } catch (Exception e) {
      throw new NotFoundException("Usuário não encontrado");
    }
  }

  public List<Recado> consultaAssunto(String search) {
    List<Recado> recadosEncontrados = recadoRepository.findByAssuntoContaining(search);
    return recadosEncontrados;
  }

  public List<Recado> consultaDescricao(String search) {
    List<Recado> recadosEncontrados = recadoRepository.findByDescricaoContaining(search);
    return recadosEncontrados;
  }
}
