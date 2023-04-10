package com.growdev.murilo.recados.service;

import com.growdev.murilo.recados.dto.RecadoDto;
import com.growdev.murilo.recados.dto.SearchDTO;
import com.growdev.murilo.recados.entities.Recado;
import com.growdev.murilo.recados.entities.User;
import com.growdev.murilo.recados.exceptions.customExceptions.BadRequestException;
import com.growdev.murilo.recados.exceptions.customExceptions.InternalServerErrorException;
import com.growdev.murilo.recados.exceptions.customExceptions.NotFoundException;
import com.growdev.murilo.recados.repository.RecadoRepository;

import com.growdev.murilo.recados.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RecadoService {
    private final RecadoRepository recadoRepository;
    private final UserRepository userRepository;
    public Recado save(RecadoDto recadoDto) {
        User user = userRepository.findById(Long.valueOf(recadoDto.getUserId())).orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        if (recadoDto.getAssunto() == null) throw new BadRequestException("Todos os campos devem estar preenchidos");
        if (recadoDto.getDescricao() == null) throw new BadRequestException("Todos os campos devem estar preenchidos");

        user.setQtdRecadosDesarquivados(user.getQtdRecadosDesarquivados() + 1);
        Recado recado = new Recado(recadoDto.getAssunto(), recadoDto.getDescricao(),
                recadoDto.getStatus(), user);
        try {
            recadoRepository.save(recado);
            userRepository.save(user);
        } catch (Exception e) {
            throw new InternalServerErrorException("Não é possível salvar o recado.");
        }

        return recado;
    }

    @Transactional(readOnly = true)
    public List<Recado> getAll(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Este usuário não existe."));
        return recadoRepository.findByUserId(id);
    }

    @Transactional(readOnly = true)
    public Recado findById(Long id) {
        return recadoRepository.findById(id).orElseThrow(() -> new NotFoundException("Recado não encontrado"));
    }

    @Transactional(readOnly = true)
    public Page<Recado> getPageableRecadoUnarchive(Long id, Pageable pageable) {
        return recadoRepository.findByUserIdPageableUnarchive(id, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Recado> getPageableRecadoArchive(Long id, Pageable pageable) {
        return recadoRepository.findByUserIdPageableArchive(id, pageable);
    }

    public Recado update(Long userId, Long id, RecadoDto recadoDto) {
        Recado recado = recadoRepository.findById(id).orElseThrow(() -> new NotFoundException("Recado não encontrado"));
        recado.setDescricao(recadoDto.getDescricao());
        recado.setAssunto(recadoDto.getAssunto());
        recado.setStatus(recadoDto.getStatus());
        if (recado.getArquivado() != recadoDto.getArquivado()) {
            recado.setArquivado(recadoDto.getArquivado());
            User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
            if (recadoDto.getArquivado()) {
                user.setQtdRecadosDesarquivados(user.getQtdRecadosDesarquivados() - 1);
                user.setQtdRecadosArquivados(user.getQtdRecadosArquivados() + 1);
                recado.setUser(user);
                recadoRepository.save(recado);
                userRepository.save(user);
                return recado;
            } else {
                user.setQtdRecadosDesarquivados(user.getQtdRecadosDesarquivados() + 1);
                user.setQtdRecadosArquivados(user.getQtdRecadosArquivados() - 1);
                recado.setUser(user);
                recadoRepository.save(recado);
                userRepository.save(user);
                return recado;
            }
        }
        try {
            recadoRepository.save(recado);
        } catch (Exception e) {
            throw new InternalServerErrorException("Não é possível atualizar o recado.");
        }
        return recado;
    }
    public void delete(Long recadoId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
        Recado recado = recadoRepository.findById(recadoId).orElseThrow(() -> new NotFoundException("Recado não encontrado."));
        recadoRepository.deleteById(recadoId);
        if (!recado.getArquivado()) {
            user.setQtdRecadosDesarquivados(user.getQtdRecadosDesarquivados() - 1);
            userRepository.save(user);
        } else {
            user.setQtdRecadosArquivados(user.getQtdRecadosArquivados() - 1);
            userRepository.save(user);
        }
    }
    public List<Recado> searchRecados(Long id, Pageable pageable, SearchDTO searchDTO) {
        List<Recado> recadosEncontrados = new ArrayList<>();
        if (Objects.equals(searchDTO.getStatus(), "todos")) {
            List<Recado> allRecados = recadoRepository.findByUserId(id);
            for (Recado recado : allRecados) {
                if (recado.getAssunto().matches("(.*)" + searchDTO.getSearch() + "(.*)")) recadosEncontrados.add(recado);

            }
            for (Recado recado : allRecados) {
                if (recado.getDescricao().matches("(.*)" + searchDTO.getSearch() + "(.*)")) recadosEncontrados.add(recado);
            }
            return recadosEncontrados;
        }
        List<Recado> recadosAssunto = recadoRepository.findRecadoAssuntoByUser(id, pageable, searchDTO.getStatus(), searchDTO.getSearch());
        List<Recado> recadosDescricao = recadoRepository.findRecadoDescricaoByUser(id, pageable, searchDTO.getStatus(), searchDTO.getSearch());
        recadosEncontrados.addAll(recadosAssunto);
        recadosEncontrados.addAll(recadosDescricao);

        return recadosEncontrados;
    }
}
