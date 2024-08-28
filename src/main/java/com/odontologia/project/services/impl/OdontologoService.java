package com.odontologia.project.services.impl;

import com.odontologia.project.models.Odontologo;
import com.odontologia.project.repository.IOdontologoRepository;
import com.odontologia.project.services.IOdontologoService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OdontologoService implements IOdontologoService {
  private final IOdontologoRepository iOdontologoRepository;

  @Override
  public Odontologo guardarOdontologo(Odontologo odontologo) {
    return iOdontologoRepository.save(odontologo);
  }

  @Override
  public Odontologo buscarOdontologoPorId(Long id) {
    return iOdontologoRepository.findById(id).orElse(null);
  }

  @Override
  public List<Odontologo> buscarTodosOdontologos() {
    return iOdontologoRepository.findAll();
  }

  @Override
  public Odontologo actualizarOdontologo(Odontologo odontologo) {
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
