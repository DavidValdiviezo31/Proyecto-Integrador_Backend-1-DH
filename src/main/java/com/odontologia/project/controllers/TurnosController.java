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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@Controller
@RequestMapping("turnos")
public class TurnosController {
  private final ITurnoService turnoService;
  private final IPacienteService pacienteService;
  private final IOdontologoService odontologoService;


  public TurnosController() {
    this.turnoService = new TurnoService();
    this.pacienteService = new PacienteService();
    this.odontologoService = new OdontologoService();
  }


  @GetMapping("/buscarPorId")
  public String buscarPorId(Model model, @RequestParam Long id) {
    Turno turno = turnoService.buscarTurno(id);
    if (turno == null) {
      model.addAttribute("error", "Turno no encontrado");
      return "error";
    }
    Paciente paciente = turno.getPaciente();
    Odontologo odontologo = turno.getOdontologo();

    model.addAttribute("paciente", paciente.getNombre() + " " + paciente.getApellido());
    model.addAttribute("odontologo", odontologo.getNombre() + " " + odontologo.getApellido());
    model.addAttribute("fecha_hora", turno.getFecha().toString() + " " + turno.getHora().toString());
    model.addAttribute("turno", turno.getId());

    return "/turnos/buscarTurno";
  }

  @PostMapping("/")
  public ResponseEntity<?> crearTurno(@RequestBody Turno turno) {
    if (pacienteService.buscarPaciente(turno.getPaciente().getId()) == null) {
      return ResponseEntity.badRequest().body("Paciente no encontrado");
    }
    if (odontologoService.buscarOdontologo(turno.getOdontologo().getId()) == null) {
      return ResponseEntity.badRequest().body("Odontólogo no encontrado");
    }
    return ResponseEntity.ok(turnoService.guardarTurno(turno));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> buscarTurno(@PathVariable Long id) {
    Turno turno = turnoService.buscarTurno(id);
    if (turno != null) {
      if (turno.getPaciente() != null) {
        turno.getPaciente().setId(turno.getPaciente().getId());
      }
      if (turno.getOdontologo() != null) {
        turno.getOdontologo().setId(turno.getOdontologo().getId());
      }
      return ResponseEntity.ok(turno);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/")
  public ResponseEntity<List<Turno>> buscarTodosTurnos() {
    return ResponseEntity.ok(turnoService.buscarTodosTurnos());
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> actualizarTurno(@PathVariable Long id, @RequestBody Turno turno) {
    if (turnoService.buscarTurno(id) == null) {
      return ResponseEntity.notFound().build();
    }
    if (pacienteService.buscarPaciente(turno.getPaciente().getId()) == null) {
      return ResponseEntity.badRequest().body("Paciente no encontrado");
    }
    if (odontologoService.buscarOdontologo(turno.getOdontologo().getId()) == null) {
      return ResponseEntity.badRequest().body("Odontólogo no encontrado");
    }
    turno.setId(id);
    Turno turnoActualizado = turnoService.actualizarTurno(turno);
    return ResponseEntity.ok(turnoActualizado);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> eliminarTurno(@PathVariable Long id) {
    Turno turnoEliminado = turnoService.eliminarTurno(id);
    if (turnoEliminado != null) {
      return ResponseEntity.ok(turnoEliminado);
    } else {
      return ResponseEntity.notFound().build();
    }
  }


}
