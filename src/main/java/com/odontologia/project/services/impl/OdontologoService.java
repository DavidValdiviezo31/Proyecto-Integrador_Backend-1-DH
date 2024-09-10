package com.odontologia.project.services.impl;

import com.odontologia.project.exceptions.EntityNotFoundException;
import com.odontologia.project.exceptions.InvalidInputException;
import com.odontologia.project.models.Odontologo;
import com.odontologia.project.repositories.IOdontologoRepository;
import com.odontologia.project.services.IOdontologoService;
import com.odontologia.project.services.errors.OdontologoErrorTypes;
import com.odontologia.project.services.errors.OdontologoErrors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OdontologoService implements IOdontologoService {
  private final IOdontologoRepository iOdontologoRepository;

  private final Logger logger = LoggerFactory.getLogger(OdontologoService.class);

  @Override
  public Odontologo guardarOdontologo(Odontologo odontologo) {
    validarOdontologo(odontologo);

    logger.info("Odontólogo con matrícula {} guardado exitosamente.", odontologo.getMatricula());
    return iOdontologoRepository.save(odontologo);
  }

  @Override
  public Odontologo buscarOdontologoPorId(Long id) {
    if (id == null)
      throw new InvalidInputException(OdontologoErrors.getErrorMessage(OdontologoErrorTypes.ID_NULL));

    return iOdontologoRepository.findById(id)
        .orElseThrow(() ->
            new EntityNotFoundException(OdontologoErrors.getErrorMessage(OdontologoErrorTypes.NOT_FOUND) + id));
  }

  @Override
  public List<Odontologo> buscarTodosOdontologos() {
    return iOdontologoRepository.findAll();
  }

  @Override
  public Odontologo actualizarOdontologo(Odontologo odontologo) {
    validarOdontologo(odontologo);

    Odontologo odontologoActualizado = buscarOdontologoPorId(odontologo.getId());
    odontologoActualizado.setNombre(odontologo.getNombre());
    odontologoActualizado.setApellido(odontologo.getApellido());
    odontologoActualizado.setMatricula(odontologo.getMatricula());

    logger.info("Odontólogo {} {} con id {} actualizado exitosamente.",
        odontologoActualizado.getNombre(),
        odontologoActualizado.getApellido(),
        odontologoActualizado.getId());
    return iOdontologoRepository.save(odontologoActualizado);
  }

  @Override
  public Odontologo eliminarOdontologoPorId(Long id) {
    Odontologo odontologoEliminado = buscarOdontologoPorId(id);

    iOdontologoRepository.deleteById(id);

    logger.info("Odontólogo con id {} eliminado exitosamente.", id);
    return odontologoEliminado;
  }

  private void validarOdontologo(Odontologo odontologo) {
    if (odontologo.getMatricula() == null)
      throw new InvalidInputException(OdontologoErrors.getErrorMessage(OdontologoErrorTypes.MATRICULA_NULL));

    if (iOdontologoRepository.existsByMatricula(odontologo.getMatricula()))
      throw new RuntimeException(OdontologoErrors.getErrorMessage(OdontologoErrorTypes.MATRICULA_EXIST) + odontologo.getMatricula());

    if (odontologo.getNombre() == null)
      throw new InvalidInputException(OdontologoErrors.getErrorMessage(OdontologoErrorTypes.NOMBRE_NULL));

    if (odontologo.getApellido() == null)
      throw new InvalidInputException(OdontologoErrors.getErrorMessage(OdontologoErrorTypes.APELLIDO_NULL));
  }
}
