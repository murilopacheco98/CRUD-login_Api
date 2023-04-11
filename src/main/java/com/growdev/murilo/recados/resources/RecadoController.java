package com.growdev.murilo.recados.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.growdev.murilo.recados.dto.RecadoDto;
import com.growdev.murilo.recados.entities.Recado;
import com.growdev.murilo.recados.service.RecadoService;

@RequestMapping("/recados")
@RestController
@CrossOrigin("*")
public class RecadoController {
    @Autowired
    private RecadoService recadoService;

    @GetMapping("/pageable/unarchive/{id}")
    public ResponseEntity<Page<Recado>> getPageableRecadoUnarchive(@PathVariable("id") Long id, Pageable pageable) {
        Page<Recado> recadosPage = recadoService.getPageableRecadoUnarchive(id, pageable);
        return new ResponseEntity<Page<Recado>>(recadosPage, HttpStatus.OK);
    }

    @GetMapping("/pageable/archive/{id}")
    public ResponseEntity<Page<Recado>> getPageableRecadoArchive(@PathVariable("id") Long id, Pageable pageable) {
        Page<Recado> recadosPage = recadoService.getPageableRecadoArchive(id, pageable);
        return new ResponseEntity<Page<Recado>>(recadosPage, HttpStatus.OK);
    }

    @GetMapping("/{id}/{search}/{status}")
    public ResponseEntity<Page<RecadoDto>> searchRecados(@PathVariable("id") Long id, @PathVariable("search") String search,
                                                         @PathVariable("status") String status, Pageable pageable) {
        Page<RecadoDto> recados = recadoService.searchRecados(id, pageable, search, status);
        return new ResponseEntity<Page<RecadoDto>>(recados, HttpStatus.OK);
    }

//    @PostMapping("/{id}")
//    public ResponseEntity<Page<RecadoDto>> searchRecados(@PathVariable("id") Long id, Pageable pageable, @RequestBody SearchDTO searchDTO) {
//        Page<RecadoDto> recados = recadoService.searchRecados(id, pageable, searchDTO);
//        return new ResponseEntity<Page<RecadoDto>>(recados, HttpStatus.OK);
//    }

    @PostMapping("/{userid}/{id}")
    public ResponseEntity<Recado> update(@PathVariable("userid") Long userId, @PathVariable("id") Long id, @RequestBody RecadoDto recadoDto) {
        Recado recado = recadoService.update(userId, id, recadoDto);
        return new ResponseEntity<Recado>(recado, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Recado> create(@RequestBody RecadoDto recadoDto) {
        Recado recado = recadoService.save(recadoDto);
        return new ResponseEntity<Recado>(recado, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userid}/{recadoid}")
    public ResponseEntity<?> deleteById(@PathVariable("recadoid") Long recadoId, @PathVariable("userid") Long userId) {
        recadoService.delete(recadoId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
