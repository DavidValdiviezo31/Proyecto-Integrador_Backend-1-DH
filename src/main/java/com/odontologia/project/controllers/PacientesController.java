package com.odontologia.project.controllers;

import com.odontologia.project.models.Paciente;
import com.odontologia.project.services.IPacienteService;
import com.odontologia.project.services.impl.PacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacientesController {
  private final IPacienteService pacienteService;

  public PacientesController() {
    this.pacienteService = new PacienteService();
  }

  @PostMapping("/")
  public ResponseEntity<Paciente> crearPaciente(@RequestBody Paciente paciente) {
    System.out.println("PACIENTE CONTROLLER - POST");
    return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Paciente> buscarPaciente(@PathVariable Long id) {
    return ResponseEntity.ok(pacienteService.buscarPaciente(id));
  }

  @GetMapping("/")
  public ResponseEntity<List<Paciente>> buscarTodosPaciente(){
    return ResponseEntity.ok(pacienteService.buscarTodosPacientes());
  }

  @PutMapping("/{id}")
  public ResponseEntity<Paciente> actualizarPaciente(@PathVariable Long id, @RequestBody Paciente paciente) {
    paciente.setId(id);
    return ResponseEntity.ok(pacienteService.actualizarPaciente(paciente));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Paciente> eliminarPaciente(@PathVariable Long id) {
    return ResponseEntity.ok(pacienteService.eliminarPaciente(id));
  }
}
