package com.odontologia.project.services.impl;

import com.odontologia.project.models.Paciente;
import com.odontologia.project.repositories.IPacienteRepository;
import com.odontologia.project.services.IPacienteService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PacienteService implements IPacienteService {
  private final IPacienteRepository iPacienteRepository;

  @Override
  public Paciente guardarPaciente(Paciente paciente) {
    return iPacienteRepository.save(paciente);
  }

  @Override
  public Paciente buscarPacientePorId(Long id) {
    return iPacienteRepository.findById(id).orElse(null);
  }

  @Override
  public List<Paciente> buscarTodosPacientes() {
    return iPacienteRepository.findAll();
  }

  @Override
  public Paciente actualizarPaciente(Paciente paciente) {
    Paciente pacienteActualizado = buscarPacientePorId(paciente.getId());

    pacienteActualizado.setNombre(paciente.getNombre());
    pacienteActualizado.setApellido(paciente.getApellido());
    pacienteActualizado.setDni(paciente.getDni());
    pacienteActualizado.setDomicilio(paciente.getDomicilio());
    pacienteActualizado.setFechaAlta(paciente.getFechaAlta());

    return iPacienteRepository.save(pacienteActualizado);
  }

  @Override
  public Paciente eliminarPacientePorId(Long id) {
    Paciente pacienteEliminado = buscarPacientePorId(id);

    if (Objects.isNull(pacienteEliminado)) return null;

    iPacienteRepository.deleteById(id);
    return pacienteEliminado;
  }
}
