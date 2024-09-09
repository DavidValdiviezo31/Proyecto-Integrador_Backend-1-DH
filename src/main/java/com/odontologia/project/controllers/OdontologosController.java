package com.odontologia.project.controllers;

import com.odontologia.project.models.Odontologo;
import com.odontologia.project.services.IOdontologoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/odontologos")
@RequiredArgsConstructor
public class OdontologosController {
  private final IOdontologoService odontologoService;

  @PostMapping
  public ResponseEntity<Odontologo> crearOdontologo(@RequestBody Odontologo odontologo) {
    Odontologo odontologoCreado = odontologoService.guardarOdontologo(odontologo);

    return Objects.isNull(odontologoCreado)
        ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
        : new ResponseEntity<>(odontologoCreado, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Odontologo> buscarOdontologo(@PathVariable Long id) {
    Odontologo odontologoBuscado = odontologoService.buscarOdontologoPorId(id);

    return Objects.isNull(odontologoBuscado)
        ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
        : new ResponseEntity<>(odontologoBuscado, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<Odontologo>> buscarTodosOdontologos() {
    return new ResponseEntity<>(odontologoService.buscarTodosOdontologos(), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Odontologo> actualizarOdontologo(@PathVariable Long id, @RequestBody Odontologo odontologo) {
    odontologo.setId(id);
    Odontologo odontologoActualizar = odontologoService.actualizarOdontologo(odontologo);

    return Objects.isNull(odontologoActualizar)
        ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
        : new ResponseEntity<>(odontologoActualizar, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Odontologo> eliminarOdontologo(@PathVariable Long id) {
    Odontologo odontologoEliminar = odontologoService.eliminarOdontologoPorId(id);

    return Objects.isNull(odontologoEliminar)
        ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
        : new ResponseEntity<>(odontologoEliminar, HttpStatus.OK);
  }
}
