package com.growdev.murilo.recados.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.growdev.murilo.recados.dto.RecadoDto;
import com.growdev.murilo.recados.entities.Recado;
import com.growdev.murilo.recados.service.RecadoService;

import java.util.List;

//@CrossOrigin("*")
@RequestMapping("/recados")
@RestController
public class RecadoController {
  @Autowired
  private RecadoService recadoService;

  @GetMapping("/getall")
  public ResponseEntity<?> getAll() {
    List<Recado> listRecado = recadoService.getAll();
    return new ResponseEntity<List<Recado>>(listRecado, HttpStatus.OK);
  }

  @GetMapping("/assunto")
  public ResponseEntity<List<Recado>> searchAssunto(@RequestParam String search) {
    List<Recado> recados = recadoService.consultaAssunto(search);
    return new ResponseEntity<List<Recado>>(recados, HttpStatus.OK);
  }

  @GetMapping("/descricao")
  public ResponseEntity<List<Recado>> searchDescricao(@RequestParam String search) {
    List<Recado> recados = recadoService.consultaDescricao(search);
    return new ResponseEntity<List<Recado>>(recados, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody RecadoDto recadoDto) {
    recadoService.update(id, recadoDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/create")
  public ResponseEntity<?> create(@RequestBody RecadoDto recadoDto) {
    recadoService.save(recadoDto);
    return new ResponseEntity<>(HttpStatus.CREATED);
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
