import com.odontologia.project.ProjectApplication;
import com.odontologia.project.exceptions.EntityNotFoundException;
import com.odontologia.project.exceptions.InvalidInputException;
import com.odontologia.project.models.Odontologo;
import com.odontologia.project.services.impl.OdontologoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest(classes = ProjectApplication.class)
@Transactional
@Rollback
public class OdontologoServiceTest {

  @Autowired
  private OdontologoService odontologoService;

  @Test
  public void testGuardarOdontologo() {
    // Arrange
    Odontologo odontologo = new Odontologo(null, 1214714065L, "Juan", "Gonzalez", null);

    // Act
    Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);

    // Assert
    assertThat(odontologoGuardado).isNotNull();
  }

  @Test
  public void testGuardarOdontologoMatriculaExistente() {
    // Arrange
    Odontologo odontologo = new Odontologo(null, 1214714065L, "Juan", "Gonzalez", null);

    // Act
    odontologoService.guardarOdontologo(odontologo);
    Throwable thrown = catchThrowable(() -> odontologoService.guardarOdontologo(odontologo));

    // Assert
    assertThat(thrown).isInstanceOf(RuntimeException.class);
  }

  @Test
  public void testGuardarOdontologoMatriculaNull() {
    // Arrange
    Odontologo odontologo = new Odontologo(null, null, "Juan", "Gonzalez", null);

    // Act
    Throwable thrown = catchThrowable(() -> odontologoService.guardarOdontologo(odontologo));

    // Assert
    assertThat(thrown).isInstanceOf(InvalidInputException.class);
  }

  @Test
  public void testGuardarOdontologoNombreNull() {
    // Arrange
    Odontologo odontologo = new Odontologo(null, 1214714065L, null, "Gonzalez", null);

    // Act
    Throwable thrown = catchThrowable(() -> odontologoService.guardarOdontologo(odontologo));

    // Assert
    assertThat(thrown).isInstanceOf(InvalidInputException.class);
  }

  @Test
  public void testGuardarOdontologoApellidoNull() {
    // Arrange
    Odontologo odontologo = new Odontologo(null, 1214714065L, "Juan", null, null);

    // Act
    Throwable thrown = catchThrowable(() -> odontologoService.guardarOdontologo(odontologo));

    // Assert
    assertThat(thrown).isInstanceOf(InvalidInputException.class);
  }

  @Test
  public void testBuscarOdontologoPorId() {
    // Arrange
    Odontologo odontologo = new Odontologo(null, 1214714065L, "Juan", "Gonzalez", null);
    Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);

    //Act
    Odontologo odontologoEncontrado = odontologoService.buscarOdontologoPorId(odontologoGuardado.getId());

    // Assert
    assertThat(odontologoEncontrado).isNotNull();
  }

  @Test
  public void testBuscarOdontologoPorIdNull() {
    // Arrange
    Long id = null;

    //Act
    Throwable thrown = catchThrowable(() -> odontologoService.buscarOdontologoPorId(id));

    // Assert
    assertThat(thrown).isInstanceOf(InvalidInputException.class);
  }

  @Test
  public void testBuscarTodosOdontologos() {
    List<Odontologo> odontologos = odontologoService.buscarTodosOdontologos();
    assertThat(odontologos).isNotNull();
  }

  @Test
  public void testActualizarNombreOdontologo() {
    // Arrange
    Odontologo odontologo = new Odontologo(null, 1214714065L, "Juan", "Gonzalez", null);
    Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);

    // Act
    odontologoGuardado.setNombre("Carlos");
    Odontologo odontologoActualizado = odontologoService.actualizarOdontologo(odontologoGuardado);

    // Assert
    assertThat(odontologoActualizado.getNombre()).isEqualTo("Carlos");
  }

  @Test
  public void testActualizarApellidoOdontologo() {
    // Arrange
    Odontologo odontologo = new Odontologo(null, 1214714065L, "Juan", "Gonzalez", null);
    Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);

    // Act
    odontologoGuardado.setApellido("Velez");
    Odontologo odontologoActualizado = odontologoService.actualizarOdontologo(odontologoGuardado);

    // Assert
    assertThat(odontologoActualizado.getApellido()).isEqualTo("Velez");
  }

  @Test
  public void testEliminarOdontologoPorId() {
    // Arrange
    Odontologo odontologo = new Odontologo(null, 1214714065L, "Carlos", "Velez", null);
    Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);

    // Act
    odontologoService.eliminarOdontologoPorId(odontologoGuardado.getId());
    Throwable thrown = catchThrowable(() -> odontologoService.buscarOdontologoPorId(odontologoGuardado.getId()));

    // Assert
    assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
  }
}

