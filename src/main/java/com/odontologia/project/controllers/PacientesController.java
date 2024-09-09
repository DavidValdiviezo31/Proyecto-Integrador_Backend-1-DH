package com.odontologia.project.controllers;

import com.odontologia.project.models.Paciente;
import com.odontologia.project.services.IPacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/pacientes")
@RequiredArgsConstructor
public class PacientesController {
  private final IPacienteService pacienteService;

  @PostMapping
  public ResponseEntity<Paciente> crearPaciente(@RequestBody Paciente paciente) {
    Paciente pacienteCreado = pacienteService.guardarPaciente(paciente);

    return Objects.isNull(pacienteCreado)
        ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
        : new ResponseEntity<>(pacienteCreado, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Paciente> buscarPaciente(@PathVariable Long id) {
    Paciente pacienteBuscado = pacienteService.buscarPacientePorId(id);

    return Objects.isNull(pacienteBuscado)
        ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
        : new ResponseEntity<>(pacienteBuscado, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<Paciente>> buscarTodosPaciente() {
    return new ResponseEntity<>(pacienteService.buscarTodosPacientes(), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Paciente> actualizarPaciente(@PathVariable Long id, @RequestBody Paciente paciente) {
    paciente.setId(id);
    Paciente pacienteActualizar = pacienteService.actualizarPaciente(paciente);
    return Objects.isNull(pacienteActualizar)
        ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
        : new ResponseEntity<>(pacienteActualizar, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Paciente> eliminarPaciente(@PathVariable Long id) {
    Paciente pacienteEliminar = pacienteService.eliminarPacientePorId(id);

    return Objects.isNull(pacienteEliminar)
        ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
        : new ResponseEntity<>(pacienteEliminar, HttpStatus.OK);
  }
}
