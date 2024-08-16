package com.odontologia.project.controllers;

import com.odontologia.project.models.Odontologo;
import com.odontologia.project.models.Paciente;
import com.odontologia.project.models.Turno;
import com.odontologia.project.services.ITurnoService;
import com.odontologia.project.services.impl.TurnoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("turnos")
public class TurnosController {
  private final ITurnoService turnoService;

  public TurnosController() {
    this.turnoService = new TurnoService();
  }

  @GetMapping("/buscarPorId")
  public String buscarPorId(Model model, @RequestParam Long id) {
    Turno turno = turnoService.buscarTurno(id);
    Paciente paciente = turno.getPaciente();
    Odontologo odontologo = turno.getOdontologo();

    model.addAttribute("paciente", paciente.getNombre() + " " + paciente.getApellido());
    model.addAttribute("odontologo", odontologo.getNombre() + " " + odontologo.getApellido());
    model.addAttribute("fecha_hora", turno.getFecha().toString() + " " + turno.getHora().toString());
    model.addAttribute("turno", turno.getId());

    return "/turnos/buscarTurno";
  }
}
