package com.odontologia.project.controllers;

import com.odontologia.project.models.Odontologo;
import com.odontologia.project.services.IOdontologoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologos")
public class OdontologosController {
  private final IOdontologoService odontologoService;

  public OdontologosController(IOdontologoService odontologoService) {
    this.odontologoService = odontologoService;
  }

  @PostMapping
  public ResponseEntity<Odontologo> crearOdontologo(@RequestBody Odontologo odontologo) {
    return new ResponseEntity<>(odontologoService.guardarOdontologo(odontologo), HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Odontologo> buscarOdontologo(@PathVariable Long id) {
    return new ResponseEntity<>(odontologoService.buscarOdontologo(id), HttpStatus.FOUND);
  }

  @GetMapping
  public ResponseEntity<List<Odontologo>> buscarTodosOdontologos(){
    return new ResponseEntity<>(odontologoService.buscarTodosOdontologos(), HttpStatus.FOUND);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Odontologo> actualizarOdontologo(@PathVariable Long id, @RequestBody Odontologo odontologo) {
    odontologo.setId(id);
    return new ResponseEntity<>(odontologoService.actualizarOdontologo(odontologo), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Odontologo> eliminarOdontologo(@PathVariable Long id) {
    return new ResponseEntity<>(odontologoService.eliminarOdontologo(id), HttpStatus.OK);
  }
}
