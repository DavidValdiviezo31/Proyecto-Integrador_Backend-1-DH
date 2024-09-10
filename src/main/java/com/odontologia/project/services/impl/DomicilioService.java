package com.odontologia.project.services.impl;

import com.odontologia.project.exceptions.EntityNotFoundException;
import com.odontologia.project.exceptions.InvalidInputException;
import com.odontologia.project.models.Domicilio;
import com.odontologia.project.repositories.IDomicilioRepository;
import com.odontologia.project.services.IDomicilioService;
import com.odontologia.project.services.errors.DomicilioErrorTypes;
import com.odontologia.project.services.errors.DomicilioErrors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DomicilioService implements IDomicilioService {
  private final IDomicilioRepository iDomicilioRepository;

  private final Logger logger = LoggerFactory.getLogger(DomicilioService.class);

  @Override
  public Domicilio guardarDomicilio(Domicilio domicilio) {
    validarNulosDomicilio(domicilio);

    logger.info("Domiclio guardado exitosamente.");
    return iDomicilioRepository.save(domicilio);
  }

  @Override
  public Domicilio buscarDomicilioPorId(Long id) {
    if (id == null) {
      throw new InvalidInputException(DomicilioErrors.getErrorMessage(DomicilioErrorTypes.ID_NULL));
    }

    return iDomicilioRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(DomicilioErrors.getErrorMessage(DomicilioErrorTypes.NOT_FOUND) + id));
  }

  @Override
  public List<Domicilio> buscarTodosDomicilios() {
    return iDomicilioRepository.findAll();
  }

  @Override
  public Domicilio actualizarDomicilio(Domicilio domicilio) {
    if (domicilio.getId() == null) {
      throw new InvalidInputException(DomicilioErrors.getErrorMessage(DomicilioErrorTypes.ID_NULL));
    }

    validarNulosDomicilio(domicilio);

    Domicilio domicilioActualizado = buscarDomicilioPorId(domicilio.getId());

    domicilioActualizado.setCalle(domicilio.getCalle());
    domicilioActualizado.setNumero(domicilio.getNumero());
    domicilioActualizado.setLocalidad(domicilio.getLocalidad());
    domicilioActualizado.setProvincia(domicilio.getProvincia());

    logger.info("Domicilio {} {}, {}, {} con id {} actualizado",
        domicilioActualizado.getCalle(),
        domicilioActualizado.getNumero(),
        domicilioActualizado.getLocalidad(),
        domicilioActualizado.getProvincia(),
        domicilioActualizado.getId());
    return iDomicilioRepository.save(domicilioActualizado);
  }

  @Override
  public Domicilio eliminarDomicilioPorId(Long id) {
    Domicilio domicilioEliminado = buscarDomicilioPorId(id);

    iDomicilioRepository.deleteById(id);

    logger.info("Domicilio con id {} eliminado exitosamente.", id);
    return domicilioEliminado;
  }

  private void validarNulosDomicilio(Domicilio domicilio) {
    if (domicilio.getCalle() == null) {
      throw new InvalidInputException(DomicilioErrors.getErrorMessage(DomicilioErrorTypes.CALLE_NULL));
    }

    if (domicilio.getNumero() == null) {
      throw new RuntimeException(DomicilioErrors.getErrorMessage(DomicilioErrorTypes.NUMERO_NULL));
    }

    if (domicilio.getLocalidad() == null) {
      throw new InvalidInputException(DomicilioErrors.getErrorMessage(DomicilioErrorTypes.LOCALIDAD_NULL));
    }

    if (domicilio.getProvincia() == null) {
      throw new InvalidInputException(DomicilioErrors.getErrorMessage(DomicilioErrorTypes.PROVINCIA_NULL));
    }
  }
}
