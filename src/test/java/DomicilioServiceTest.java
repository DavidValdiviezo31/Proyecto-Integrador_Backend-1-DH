import com.odontologia.project.ProjectApplication;
import com.odontologia.project.exceptions.EntityNotFoundException;
import com.odontologia.project.models.Domicilio;
import com.odontologia.project.services.impl.DomicilioService;
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
public class DomicilioServiceTest {

  @Autowired
  private DomicilioService domicilioService;

  @Test
  public void testGuardarDomicilio() {
    // Arrange
    Domicilio domicilio = new Domicilio(null, "Calle P sherman", 524, "Medellín", "Antioquia");

    // Act
    Domicilio domicilioGuardado = domicilioService.guardarDomicilio(domicilio);

    // Assert
    assertThat(domicilioGuardado).isNotNull();
  }

  @Test
  void testBuscarDomicilioPorId() {
    // Arrange
    Domicilio domicilio = new Domicilio(null, "Calle P sherman", 524, "Medellín", "Antioquia");

    // Act
    Domicilio domicilioGuardado = domicilioService.guardarDomicilio(domicilio);
    Domicilio domicilioEncontrado = domicilioService.buscarDomicilioPorId(domicilioGuardado.getId());

    // Assert
    assertThat(domicilioEncontrado).isNotNull();
  }

  @Test
  void testBuscarTodosDomicilios() {
    List<Domicilio> domicilios = domicilioService.buscarTodosDomicilios();
    assertThat(domicilios).isNotNull();
  }

  @Test
  void testActualizarCalleDomicilio() {
    // Arrange
    Domicilio domicilio = new Domicilio(null, "Calle P sherman", 524, "Medellín", "Antioquia");
    Domicilio domicilioGuardado = domicilioService.guardarDomicilio(domicilio);

    // Act
    domicilioGuardado.setCalle("Calle Juan23");
    Domicilio domicilioActualizado = domicilioService.actualizarDomicilio(domicilioGuardado);

    // Assert
    assertThat(domicilioActualizado.getCalle()).isEqualTo("Calle Juan23");
  }

  @Test
  void testActualizarNumeroDomicilio() {
    // Arrange
    Domicilio domicilio = new Domicilio(null, "Calle P sherman", 524, "Medellín", "Antioquia");
    Domicilio domicilioGuardado = domicilioService.guardarDomicilio(domicilio);

    // Act
    domicilioGuardado.setNumero(456);
    Domicilio domicilioActualizado = domicilioService.actualizarDomicilio(domicilioGuardado);

    // Assert
    assertThat(domicilioActualizado.getNumero()).isEqualTo(456);
  }

  @Test
  void testEliminarDomicilioPorId() {
    // Arrange
    Domicilio domicilio = new Domicilio(null, "Calle Juan23", 366, "B", "USA");
    Domicilio domicilioGuardado = domicilioService.guardarDomicilio(domicilio);

    // Act
    domicilioService.eliminarDomicilioPorId(domicilioGuardado.getId());
    Throwable thrown = catchThrowable(() -> domicilioService.buscarDomicilioPorId(domicilioGuardado.getId()));

    // Assert
    assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
  }
}
