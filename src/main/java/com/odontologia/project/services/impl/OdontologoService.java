package com.odontologia.project.services.impl;

import com.odontologia.project.exceptions.EntityNotFoundException;
import com.odontologia.project.exceptions.InvalidInputException;
import com.odontologia.project.models.Odontologo;
import com.odontologia.project.repositories.IOdontologoRepository;
import com.odontologia.project.services.IOdontologoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OdontologoService implements IOdontologoService {
  private final IOdontologoRepository iOdontologoRepository;

  @Override
  public Odontologo guardarOdontologo(Odontologo odontologo) {
    if (iOdontologoRepository.existsByMatricula(odontologo.getMatricula())) {
      throw new RuntimeException("Ya existe un odontólogo con la matrícula " + odontologo.getMatricula());
    }
    if (odontologo.getMatricula() == null) {
      throw new InvalidInputException("El odontólogo o su matrícula no pueden ser nulos.");
    }
    return iOdontologoRepository.save(odontologo);
  }

  @Override
  public Odontologo buscarOdontologoPorId(Long id) {
    return iOdontologoRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("No existe un odontologo con ese id: " + id));
  }

  @Override
  public List<Odontologo> buscarTodosOdontologos() {
    return iOdontologoRepository.findAll();
  }

  @Override
  public Odontologo actualizarOdontologo(Odontologo odontologo) {
    if (odontologo == null || odontologo.getId() == null) {
      throw new InvalidInputException("El odontólogo o su ID no pueden ser nulos.");
    }
    Odontologo odontologoActualizado = buscarOdontologoPorId(odontologo.getId());
    odontologoActualizado.setNombre(odontologo.getNombre());
    odontologoActualizado.setApellido(odontologo.getApellido());
    odontologoActualizado.setMatricula(odontologo.getMatricula());

    return iOdontologoRepository.save(odontologoActualizado);
  }

  public Odontologo eliminarOdontologoPorId(Long id) {
    Odontologo odontologoEliminado = buscarOdontologoPorId(id);

    if (Objects.isNull(odontologoEliminado)) return null;

    iOdontologoRepository.deleteById(id);
    return odontologoEliminado;
  }
}
