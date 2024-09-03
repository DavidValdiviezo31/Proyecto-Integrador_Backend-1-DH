import com.odontologia.project.ProjectApplication;

import com.odontologia.project.models.Domicilio;
import com.odontologia.project.models.Paciente;
import com.odontologia.project.services.impl.DomicilioService;
import com.odontologia.project.services.impl.PacienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(classes = ProjectApplication.class)
@Transactional
@Rollback
public class PacienteServiceTest {

  @Autowired
  private PacienteService pacienteService;

  @Autowired
  private DomicilioService domicilioService;

  @Test
  public void testGuardarPaciente() {
    // Arrange
    Domicilio domicilio = new Domicilio(null, "Calle Z", 999, "Ciudad Z", "Provincia Z");
    Domicilio domicilioGuardado = domicilioService.guardarDomicilio(domicilio);
    Paciente paciente = new Paciente(null,1027887642L,"Alexa","Monsalve",domicilioGuardado, LocalDate.parse("2023-01-01"),null);

    // Act
    Paciente pacienteGuardado = pacienteService.guardarPaciente(paciente);

    // Assert
    assertThat(pacienteGuardado).isNotNull();
  }

  @Test
  public void testBuscarPacientePorId() {
    // Arrange
    Domicilio domicilio = new Domicilio(null, "Calle Z", 999, "Ciudad Z", "Provincia Z");
    Domicilio domicilioGuardado = domicilioService.guardarDomicilio(domicilio);
    Paciente paciente = new Paciente(null,1027887642L,"Alexa","Monsalve",domicilioGuardado, LocalDate.parse("2023-01-01"),null);
    Paciente pacienteGuardado = pacienteService.guardarPaciente(paciente);

    // Act
    Paciente pacienteEncontrado = pacienteService.buscarPacientePorId(pacienteGuardado.getId());

    // Assert
    assertThat(pacienteEncontrado).isNotNull();
  }

  @Test
  public void testBuscarTodosPacientes() {
    List<Paciente> pacientes = pacienteService.buscarTodosPacientes();
    assertThat(pacientes).isNotNull();
  }

  @Test
  public void testActualizarNombrePaciente() {
    // Arrange
    Domicilio domicilio = new Domicilio(null, "Calle Z", 999, "Ciudad Z", "Provincia Z");
    Domicilio domicilioGuardado = domicilioService.guardarDomicilio(domicilio);
    Paciente paciente = new Paciente(null, 1027887642L, "Alexa", "Monsalve", domicilioGuardado, LocalDate.parse("2023-01-01"), null);
    Paciente pacienteGuardado = pacienteService.guardarPaciente(paciente);

    // Act
    pacienteGuardado.setNombre("Alecsandra");
    Paciente pacienteActualizado = pacienteService.actualizarPaciente(pacienteGuardado);

    // Assert
    assertThat(pacienteActualizado.getNombre()).isEqualTo("Alecsandra");
  }

  @Test
  public void testActualizarApellidoPaciente() {
    // Arrange
    Domicilio domicilio = new Domicilio(null, "Calle Z", 999, "Ciudad Z", "Provincia Z");
    Domicilio domicilioGuardado = domicilioService.guardarDomicilio(domicilio);
    Paciente paciente = new Paciente(null, 1027887642L, "Alexa", "Monsalve", domicilioGuardado, LocalDate.parse("2023-01-01"), null);
    Paciente pacienteGuardado = pacienteService.guardarPaciente(paciente);

    // Act
    pacienteGuardado.setApellido("Piedrahita");
    Paciente pacienteActualizado = pacienteService.actualizarPaciente(pacienteGuardado);

    // Assert
    assertThat(pacienteActualizado.getApellido()).isEqualTo("Piedrahita");
  }

  @Test
  public void testEliminarPacientePorId() {
    // Arrange
    Domicilio domicilio = new Domicilio(null, "Calle Z", 999, "Ciudad Z", "Provincia Z");
    Domicilio domicilioGuardado = domicilioService.guardarDomicilio(domicilio);
    Paciente paciente = new Paciente(null, 1027887642L, "Alecsandra", "Piedrahita", domicilioGuardado, LocalDate.parse("2023-01-01"), null);
    Paciente pacienteGuardado = pacienteService.guardarPaciente(paciente);

    // Act
    pacienteService.eliminarPacientePorId(pacienteGuardado.getId());
    Paciente pacienteEncontrado = pacienteService.buscarPacientePorId(pacienteGuardado.getId());

    // Asser
    assertThat(pacienteEncontrado).isNull();
  }
}

