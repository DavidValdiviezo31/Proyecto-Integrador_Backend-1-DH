package com.odontologia.project.controllers;

import com.odontologia.project.models.Odontologo;
import com.odontologia.project.models.Paciente;
import com.odontologia.project.models.Turno;
import com.odontologia.project.services.IOdontologoService;
import com.odontologia.project.services.IPacienteService;
import com.odontologia.project.services.ITurnoService;
import org.springframework.http.HttpStatus;
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


  public TurnosController(ITurnoService turnoService, IPacienteService pacienteService, IOdontologoService odontologoService) {
    this.turnoService = turnoService;
    this.pacienteService = pacienteService;
    this.odontologoService = odontologoService;
  }

  @PostMapping
  public ResponseEntity<Turno> crearTurno(@RequestBody Turno turno, @RequestParam("paciente") Long pacienteId, @RequestParam("odontologo") Long odontologoId) {
    Paciente paciente = pacienteService.buscarPaciente(pacienteId);
    Odontologo odontologo = odontologoService.buscarOdontologo(odontologoId);

    if (Objects.isNull(odontologo) || Objects.isNull(paciente)) return ResponseEntity.notFound().build();

    turno.setPaciente(paciente);
    turno.setOdontologo(odontologo);

    return new ResponseEntity<>(turnoService.guardarTurno(turno), HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Turno> buscarTurno(@PathVariable Long id) {
    return new ResponseEntity<>(turnoService.buscarTurno(id), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<Turno>> buscarTodosTurnos() {
    return new ResponseEntity<>(turnoService.buscarTodosTurnos(), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Turno> actualizarTurno(@PathVariable Long id, @RequestBody Turno turno) {
    turno.setId(id);
    return new ResponseEntity<>(turnoService.actualizarTurno(turno), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Turno> eliminarTurno(@PathVariable Long id) {
    return new ResponseEntity<>(turnoService.eliminarTurno(id), HttpStatus.OK);
  }
}
