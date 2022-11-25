package com.growdev.murilo.recados.service;

import com.growdev.murilo.recados.dto.RecadoDto;
import com.growdev.murilo.recados.entities.Recado;
import com.growdev.murilo.recados.exceptions.customExceptions.BadRequestException;
import com.growdev.murilo.recados.exceptions.customExceptions.ForbiddenException;
import com.growdev.murilo.recados.exceptions.customExceptions.InternalServerErrorException;
import com.growdev.murilo.recados.exceptions.customExceptions.NotFoundException;
import com.growdev.murilo.recados.repository.RecadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Transactional
@Service
public class RecadoService {
    @Autowired
    RecadoRepository recadoRepository;

    public Recado save(RecadoDto recadoDto) {
        if (recadoDto.getAssunto() == null) {
            throw new BadRequestException("Todos os campos devem estar preenchidos");
        }
        if (recadoDto.getDescricao() == null) {
            throw new BadRequestException("Todos os campos devem estar preenchidos");
        }
        if (recadoDto.getUser() == null) {
            throw new ForbiddenException("Recado sem usuário.");
        }

        Recado recado = new Recado(recadoDto.getAssunto(), recadoDto.getDescricao(),
                recadoDto.getStatus(), recadoDto.getUser());
        try {
            recadoRepository.save(recado);
        } catch (Exception e) {
            throw new InternalServerErrorException("Não é possível salvar o recado.");
        }
        return recado;
    }

    @Transactional(readOnly = true)
    public List<Recado> getAll(Long id) {
        try {
            return recadoRepository.findByUser(id);
        } catch (Exception e) {
            throw new InternalServerErrorException("Problema interno na requisição dos recados.");
        }
    }

    @Transactional(readOnly = true)
    public Recado findById(Long id) {
        Recado recado = recadoRepository.findById(id).orElseThrow(() -> new NotFoundException("Recado não encontrado"));
        return recado;
    }

    public Recado update(Long id, RecadoDto recadoDto) {
        Recado recado = recadoRepository.findById(id).orElseThrow(() -> new NotFoundException("Recado não encontrado"));
        recado.setDescricao(recadoDto.getDescricao());
        recado.setAssunto(recadoDto.getAssunto());
        recado.setStatus(recadoDto.getStatus());
        recado.setArquivado(recadoDto.getArquivado());
        try {
//      recadoRepository.deleteById(id);
            recadoRepository.save(recado);
        } catch (Exception e) {
            throw new InternalServerErrorException("Não é possível atualizar o recado.");
        }
        return recado;
    }

    public void delete(Long id) {
        try {
            recadoRepository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException("Usuário não encontrado");
        }
    }

    public List<Recado> searchRecados(Long id, String search, String status) {
        if (Objects.equals(status, "todos")) {
            List<Recado> allRecados = recadoRepository.findByUser(id);
            List<Recado> recadosEncontrados = new ArrayList<>();
            for (Recado recado : allRecados) {
                if (recado.getAssunto().matches("(.*)" + search + "(.*)")) {
                    recadosEncontrados.add(recado);
                }
            }
            for (Recado recado : allRecados) {
                if (recado.getDescricao().matches("(.*)" + search + "(.*)")) {
                    recadosEncontrados.add(recado);
                }
            }
            return recadosEncontrados;
        }
        List<Recado> recadosEncontrados = new ArrayList<>();
        List<Recado> recadosAssunto = recadoRepository.findRecadoAssuntoByUser(id, status, search);
        List<Recado> recadosDescricao = recadoRepository.findRecadoDescricaoByUser(id, status, search);
        recadosEncontrados.addAll(recadosAssunto);
        recadosEncontrados.addAll(recadosDescricao);
        return recadosEncontrados;
    }

//    public List<Recado> consultaDescricao(String search, String status) {
//        if (Objects.equals(status, "todos")) {
//            return recadoRepository.findByDescricaoContaining(search);
//        }
//        List<Recado> recadosStatus = recadoRepository.findByStatusContaining(status);
//        List<Recado> recadosEncontrados = new ArrayList<>();
//        for (Recado recado : recadosStatus) {
//            if (recado.getDescricao().matches("(.*)" + search + "(.*)")) {
//                recadosEncontrados.add(recado);
//            }
//        }
//        return recadosEncontrados;
//    }
}
