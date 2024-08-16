package com.odontologia.project;

import com.odontologia.project.models.Odontologo;
import com.odontologia.project.models.Paciente;
import com.odontologia.project.services.impl.OdontologoService;
import com.odontologia.project.services.impl.PacienteService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class ProjectApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProjectApplication.class, args);

    PacienteService pacienteService = new PacienteService();
    OdontologoService odontologoService = new OdontologoService();

    Paciente p1 = new Paciente(1313077792L,"David", "Valdiviezo", "Portoviejo", LocalDate.of(2024, 8, 20));

    pacienteService.guardarPaciente(p1);
//    pacienteService.buscarTodosPacientes();
//    System.out.println(pacienteService.buscarPaciente(1313077792L));
//    Paciente p2 = new Paciente(1313077792L,"Andres", "Valdiviezo", "Portoviejo", LocalDate.of(2024, 8, 20));
//    pacienteService.actualizarPaciente(p2);
//    pacienteService.eliminarPaciente(1313077792L);
//    System.out.println(pacienteService.buscarPaciente(1313077792L));

    Odontologo od1 = new Odontologo(1313077795L, "Andres", "Intriago");

    odontologoService.guardarOdontologo(od1);
//    odontologoService.buscarTodosOdontologos();
//    System.out.println(odontologoService.buscarOdontologo(1313077795L));
//    Odontologo od2 = new Odontologo(1313077795L, "David", "Intriago");
//    odontologoService.actualizarOdontologo(od2);
//    odontologoService.eliminarOdontologo(1313077795L);
//    System.out.println(odontologoService.buscarOdontologo(1313077795L));

  }

}
