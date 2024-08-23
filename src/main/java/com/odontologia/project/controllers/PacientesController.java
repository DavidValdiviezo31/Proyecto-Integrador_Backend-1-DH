package com.odontologia.project.controllers;

import com.odontologia.project.models.Paciente;
import com.odontologia.project.services.IPacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacientesController {
  private final IPacienteService pacienteService;

  public PacientesController(IPacienteService pacienteService) {
    this.pacienteService = pacienteService;
  }

  @PostMapping
  public ResponseEntity<Paciente> crearPaciente(@RequestBody Paciente paciente) {
    return new ResponseEntity<>(pacienteService.guardarPaciente(paciente), HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Paciente> buscarPaciente(@PathVariable Long id) {
    return new ResponseEntity<>(pacienteService.buscarPaciente(id), HttpStatus.FOUND);
  }

  @GetMapping
  public ResponseEntity<List<Paciente>> buscarTodosPaciente(){
    return new ResponseEntity<>(pacienteService.buscarTodosPacientes(), HttpStatus.FOUND);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Paciente> actualizarPaciente(@PathVariable Long id, @RequestBody Paciente paciente) {
    paciente.setId(id);
    return new ResponseEntity<>(pacienteService.actualizarPaciente(paciente), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Paciente> eliminarPaciente(@PathVariable Long id) {
    return new ResponseEntity<>(pacienteService.eliminarPaciente(id), HttpStatus.OK);
  }
}
