package com.growdev.murilo.recados.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.growdev.murilo.recados.dto.RecadoDto;
import com.growdev.murilo.recados.entities.Recado;
import com.growdev.murilo.recados.service.RecadoService;

import java.util.List;


@RequestMapping("/recados")
@RestController
@CrossOrigin("*")
public class RecadoController {
    @Autowired
    private RecadoService recadoService;

//    @GetMapping("/getall/{id}")
//    public ResponseEntity<?> getAll(@PathVariable Long id) {
//        List<Recado> listRecado = recadoService.getAll(id);
//        return new ResponseEntity<List<Recado>>(listRecado, HttpStatus.OK);
//    }

    @GetMapping("/pageable/unarchive/{id}")
    public ResponseEntity<Page<Recado>> getPageableRecadoUnarchive(@PathVariable("id") Long id, @RequestParam Integer page, @RequestParam Integer size) {
        Page<Recado> recadosPage = recadoService.getPageableRecadoUnarchive(id, page, size);
        return new ResponseEntity<Page<Recado>>(recadosPage, HttpStatus.OK);
    }

    @GetMapping("/pageable/archive/{id}")
    public ResponseEntity<Page<Recado>> getPageableRecadoArchive(@PathVariable("id") Long id, @RequestParam Integer page, @RequestParam Integer size) {
        Page<Recado> recadosPage = recadoService.getPageableRecadoArchive(id, page, size);
        return new ResponseEntity<Page<Recado>>(recadosPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Recado>> searchRecados(@PathVariable("id") Long id, @RequestParam String search, @RequestParam String status) {
        List<Recado> recados = recadoService.searchRecados(id, search, status);
        return new ResponseEntity<List<Recado>>(recados, HttpStatus.OK);
    }

    @PostMapping("/{userid}/{id}")
    public ResponseEntity<Recado> update(@PathVariable("userid") Long userId, @PathVariable("id") Long id, @RequestBody RecadoDto recadoDto) {
        Recado recado = recadoService.update(userId, id, recadoDto);
        return new ResponseEntity<Recado>(recado, HttpStatus.OK);
    }

    @PostMapping("/create/{userid}")
    public ResponseEntity<Recado> create(@RequestBody RecadoDto recadoDto, @PathVariable("userid") Long userId) {
        Recado recado = recadoService.save(recadoDto, userId);
        return new ResponseEntity<Recado>(recado, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userid}/{recadoid}")
    public ResponseEntity<?> deleteById(@PathVariable("recadoid") Long recadoId, @PathVariable("userid") Long userId) {
        recadoService.delete(recadoId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<Recado> findid(@PathVariable("id") Long id) {
    //   Recado recado = service.findById(id);
    //   ResponseEntity.status(HttpStatus.OK);
    //   return ResponseEntity.ok(recado);
    // }

    //  @GetMapping("/assunto")
    //  public ResponseEntity<List<Recado>> searchAssunto(@RequestParam String search, @RequestParam String status) {
    //    List<Recado> recados = recadoService.consultaAssunto(search, status);
    //    return new ResponseEntity<List<Recado>>(recados, HttpStatus.OK);
    //  }
    //
    //  @GetMapping("/descricao")
    //  public ResponseEntity<List<Recado>> searchDescricao(@RequestParam String search, @RequestParam String status) {
    //    List<Recado> recados = recadoService.consultaDescricao(search, status);
    //    return new ResponseEntity<List<Recado>>(recados, HttpStatus.OK);
    //  }

}
