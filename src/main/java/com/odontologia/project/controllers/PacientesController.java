package com.odontologia.project.controllers;

import com.odontologia.project.models.Paciente;
import com.odontologia.project.services.IPacienteService;
import com.odontologia.project.services.impl.PacienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("pacientes")
public class PacientesController {
  private final IPacienteService pacienteService;

  public PacientesController() {
    this.pacienteService = new PacienteService();
  }

  @GetMapping("/buscarPorId")
  public String buscarPaciente(Model model, @RequestParam Long dni) {
    Paciente paciente = pacienteService.buscarPaciente(dni);

    model.addAttribute("nombre", paciente.getNombre());
    model.addAttribute("apellido", paciente.getApellido());

    return "/pacientes/buscarPaciente";
  }
}
