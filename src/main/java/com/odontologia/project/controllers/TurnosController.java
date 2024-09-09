package com.odontologia.project.controllers;

import com.odontologia.project.models.Turno;
import com.odontologia.project.services.ITurnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/turnos")
@RequiredArgsConstructor
public class TurnosController {
  private final ITurnoService turnoService;

  @PostMapping
  public ResponseEntity<Turno> crearTurno(@RequestBody Turno turno) {
    Turno turnoCreado = turnoService.guardarTurno(turno);

    return Objects.isNull(turnoCreado)
        ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
        : new ResponseEntity<>(turnoCreado, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Turno> buscarTurno(@PathVariable Long id) {
    Turno turnoBuscado = turnoService.buscarTurnoPorId(id);

    return Objects.isNull(turnoBuscado)
        ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
        : new ResponseEntity<>(turnoBuscado, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<Turno>> buscarTodosTurnos() {
    return new ResponseEntity<>(turnoService.buscarTodosTurnos(), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Turno> actualizarTurno(@PathVariable Long id, @RequestBody Turno turno) {
    turno.setId(id);
    Turno turnoActualizar = turnoService.actualizarTurno(turno);

    return Objects.isNull(turnoActualizar)
        ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
        : new ResponseEntity<>(turnoActualizar, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Turno> eliminarTurno(@PathVariable Long id) {
    Turno turnoEliminar = turnoService.eliminarTurnoPorId(id);

    return Objects.isNull(turnoEliminar)
        ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
        : new ResponseEntity<>(turnoEliminar, HttpStatus.OK);
  }

}
