package com.growdev.murilo.recados.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.growdev.murilo.recados.dto.RecadoDto;
import com.growdev.murilo.recados.entities.Recado;
import com.growdev.murilo.recados.service.RecadoService;

import java.util.List;

@CrossOrigin("*")
@RequestMapping("/recados")
@RestController
public class RecadoController {
  @Autowired
  private RecadoService recadoService;

  @GetMapping("/getall/{id}")
  public ResponseEntity<?> getAll(@PathVariable Long id) {
    List<Recado> listRecado = recadoService.getAll(id);
    return new ResponseEntity<List<Recado>>(listRecado, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<List<Recado>> searchRecados(@PathVariable("id") Long id, @RequestParam String search, @RequestParam String status) {
    List<Recado> recados = recadoService.searchRecados(id, search, status);
    return new ResponseEntity<List<Recado>>(recados, HttpStatus.OK);
  }

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

  @PutMapping("/{id}")
  public ResponseEntity<Recado> update(@PathVariable("id") Long id, @RequestBody RecadoDto recadoDto) {
    Recado recado = recadoService.update(id, recadoDto);
    return new ResponseEntity<Recado>(recado, HttpStatus.OK);
  }

  @PostMapping("/create")
  public ResponseEntity<Recado> create(@RequestBody RecadoDto recadoDto) {
    Recado recado = recadoService.save(recadoDto);
    return new ResponseEntity<Recado>(recado, HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
    recadoService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  
  // @GetMapping("/{id}")
  // public ResponseEntity<Recado> findid(@PathVariable("id") Long id) {
  //   Recado recado = service.findById(id);
  //   ResponseEntity.status(HttpStatus.OK);
  //   return ResponseEntity.ok(recado);
  // }
}
