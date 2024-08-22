package com.odontologia.project.controllers;

import com.odontologia.project.models.Odontologo;
import com.odontologia.project.services.IOdontologoService;
import com.odontologia.project.services.impl.OdontologoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologos")
public class OdontologosController {
  private final IOdontologoService odontologoService;

  public OdontologosController() {
    this.odontologoService = new OdontologoService();
  }

  @PostMapping
  public ResponseEntity<Odontologo> crearOdontologo(@RequestBody Odontologo odontologo) {
    return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Odontologo> buscarOdontologo(@PathVariable Long id) {
    return ResponseEntity.ok(odontologoService.buscarOdontologo(id));
  }

  @GetMapping
  public ResponseEntity<List<Odontologo>> buscarTodosOdontologos(){
    return ResponseEntity.ok(odontologoService.buscarTodosOdontologos());
  }

  @PutMapping("/{id}")
  public ResponseEntity<Odontologo> actualizarOdontologo(@PathVariable Long id, @RequestBody Odontologo odontologo) {
    odontologo.setId(id);
    return ResponseEntity.ok(odontologoService.actualizarOdontologo(odontologo));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Odontologo> eliminarOdontologo(@PathVariable Long id) {
    return ResponseEntity.ok(odontologoService.eliminarOdontologo(id));
  }
}
