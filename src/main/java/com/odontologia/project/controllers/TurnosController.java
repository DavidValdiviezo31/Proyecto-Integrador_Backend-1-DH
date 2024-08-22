package com.odontologia.project.controllers;

import com.odontologia.project.models.Odontologo;
import com.odontologia.project.models.Paciente;
import com.odontologia.project.models.Turno;
import com.odontologia.project.services.IOdontologoService;
import com.odontologia.project.services.IPacienteService;
import com.odontologia.project.services.ITurnoService;
import com.odontologia.project.services.impl.OdontologoService;
import com.odontologia.project.services.impl.PacienteService;
import com.odontologia.project.services.impl.TurnoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/turnos")
public class TurnosController {
  private final ITurnoService turnoService;
  private final IPacienteService pacienteService;
  private final IOdontologoService odontologoService;


  public TurnosController() {
    this.turnoService = new TurnoService();
    this.pacienteService = new PacienteService();
    this.odontologoService = new OdontologoService();
  }

  @PostMapping("/")
  public ResponseEntity<Turno> crearTurno(@RequestBody Turno turno, @RequestParam("paciente") Long pacienteId, @RequestParam("odontologo") Long odontologoId) {
    Paciente paciente = pacienteService.buscarPaciente(pacienteId);
    Odontologo odontologo = odontologoService.buscarOdontologo(odontologoId);

    if (Objects.isNull(odontologo) || Objects.isNull(paciente)) return ResponseEntity.notFound().build();

    turno.setPaciente(paciente);
    turno.setOdontologo(odontologo);

    return ResponseEntity.ok(turnoService.guardarTurno(turno));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Turno> buscarTurno(@PathVariable Long id) {
    return ResponseEntity.ok(turnoService.buscarTurno(id));
  }

  @GetMapping("/")
  public ResponseEntity<List<Turno>> buscarTodosTurnos() {
    return ResponseEntity.ok(turnoService.buscarTodosTurnos());
  }

  @PutMapping("/{id}")
  public ResponseEntity<Turno> actualizarTurno(@PathVariable Long id, @RequestBody Turno turno) {
    turno.setId(id);
    return ResponseEntity.ok(turnoService.actualizarTurno(turno));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Turno> eliminarTurno(@PathVariable Long id) {
    return ResponseEntity.ok(turnoService.eliminarTurno(id));
  }
}
