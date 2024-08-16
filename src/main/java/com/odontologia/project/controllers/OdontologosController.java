package com.odontologia.project.controllers;

import com.odontologia.project.models.Odontologo;
import com.odontologia.project.services.IOdontologoService;
import com.odontologia.project.services.impl.OdontologoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("odontologos")
public class OdontologosController {
  private final IOdontologoService odontologoService;

  public OdontologosController() {
    this.odontologoService = new OdontologoService();
  }

  @GetMapping("/buscarPorId")
  public String buscarOdontologo(Model model, @RequestParam Long matricula) {
    Odontologo odontologo = odontologoService.buscarOdontologo(matricula);

    model.addAttribute("nombre", odontologo.getNombre());
    model.addAttribute("apellido", odontologo.getApellido());

    return "/odontologos/buscarOdontologo";
  }

  @GetMapping("/buscarTodos")
  public String buscarTodosOdontologos(Model model){
    List<Odontologo> odontologoList=  odontologoService.buscarTodosOdontologos();

    model.addAllAttributes(odontologoList);

    return "/odontologos/buscarTodos";
  }
}
