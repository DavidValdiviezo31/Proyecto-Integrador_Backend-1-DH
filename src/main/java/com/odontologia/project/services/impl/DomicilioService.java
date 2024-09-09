package com.odontologia.project.services.impl;

import com.odontologia.project.exceptions.EntityNotFoundException;
import com.odontologia.project.exceptions.InvalidInputException;
import com.odontologia.project.models.Domicilio;
import com.odontologia.project.repositories.IDomicilioRepository;
import com.odontologia.project.services.IDomicilioService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DomicilioService implements IDomicilioService {
  private final IDomicilioRepository iDomicilioRepository;

  @Override
  public Domicilio guardarDomicilio(Domicilio domicilio) {
    if (domicilio == null || domicilio.getId() == null) {
      throw new InvalidInputException("El domicilio o su ID no pueden ser nulos.");
    }
    return iDomicilioRepository.save(domicilio);
  }

  @Override
  public Domicilio buscarDomicilioPorId(Long id) {
    return iDomicilioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("No existe el domicilio con el id: " + id));
  }

  @Override
  public List<Domicilio> buscarTodosDomicilios() {
    return iDomicilioRepository.findAll();
  }

  @Override
  public Domicilio actualizarDomicilio(Domicilio domicilio) {
    if (domicilio == null || domicilio.getId() == null) {
      throw new InvalidInputException("El domicilio o su ID no pueden ser nulos.");
    }
    Domicilio domicilioActualizado = buscarDomicilioPorId(domicilio.getId());

    domicilioActualizado.setCalle(domicilio.getCalle());
    domicilioActualizado.setNumero(domicilio.getNumero());
    domicilioActualizado.setLocalidad(domicilio.getLocalidad());
    domicilioActualizado.setProvincia(domicilio.getProvincia());

    return iDomicilioRepository.save(domicilioActualizado);
  }

  @Override
  public Domicilio eliminarDomicilioPorId(Long id) {
    Domicilio domicilioEliminado = buscarDomicilioPorId(id);

    if (Objects.isNull(domicilioEliminado)) return null;

    iDomicilioRepository.deleteById(id);
    return domicilioEliminado;
  }
}
