import com.odontologia.project.ProjectApplication;
import com.odontologia.project.exceptions.BadRequestException;
import com.odontologia.project.exceptions.EntityNotFoundException;
import com.odontologia.project.exceptions.InvalidInputException;
import com.odontologia.project.models.Domicilio;
import com.odontologia.project.models.Odontologo;
import com.odontologia.project.models.Paciente;
import com.odontologia.project.models.Turno;
import com.odontologia.project.services.impl.DomicilioService;
import com.odontologia.project.services.impl.OdontologoService;
import com.odontologia.project.services.impl.PacienteService;
import com.odontologia.project.services.impl.TurnoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@SpringBootTest(classes = ProjectApplication.class)
@Transactional
@Rollback
public class TurnoServiceTest {

  @Autowired
  private TurnoService turnoService;

  @Autowired
  private OdontologoService odontologoService;

  @Autowired
  private PacienteService pacienteService;

  @Autowired
  private DomicilioService domicilioService;

  @Test
  public void testGuardarTurno() {
    // Arrange
    Turno turno = new Turno(null, LocalDate.parse("2024-06-24"), LocalTime.parse("10:00"), null, null);

    // Act
    Throwable thrown = catchThrowable(() -> turnoService.guardarTurno(turno));

    // Assert
    assertThat(thrown).isInstanceOf(BadRequestException.class);
  }

  @Test
  public void testGuardarTurnoConFechaNula() {
    // Arrange
    Paciente paciente = new Paciente(null, 123456789L, "Pedro", "González", null, LocalDate.parse("2024-01-01"), null);
    Odontologo odontologo = new Odontologo(null, 123456789L, "Pedro", "González", null);
    Turno turno = new Turno(null, null, LocalTime.parse("10:00"), paciente, odontologo);

    // Act
    Throwable thrown = catchThrowable(() -> turnoService.guardarTurno(turno));

    // Assert
    assertThat(thrown).isInstanceOf(InvalidInputException.class);
  }

  @Test
  public void testGuardarTurnoConHoraNula() {
    // Arrange
    Paciente paciente = new Paciente(null, 123456789L, "Pedro", "González", null, LocalDate.parse("2024-01-01"), null);
    Odontologo odontologo = new Odontologo(null, 123456888L, "Pablo", "Velez", null);
    Turno turno = new Turno(null, LocalDate.parse("2024-06-24"), null, paciente, odontologo);

    // Act
    Throwable thrown = catchThrowable(() -> turnoService.guardarTurno(turno));

    // Assert
    assertThat(thrown).isInstanceOf(InvalidInputException.class);
  }

  @Test
  public void testBuscarTurnoPorId() {
    // Arrange
    Odontologo odontologo = new Odontologo(null, 123456789L, "Pedro", "González", null);
    Paciente paciente = new Paciente(null, 987654321L, "Luis", "Pérez", null, LocalDate.parse("2024-01-01"), null);

    odontologoService.guardarOdontologo(odontologo);
    pacienteService.guardarPaciente(paciente);

    Turno turno = new Turno(null, LocalDate.parse("2023-04-29"), LocalTime.parse("08:00"), paciente, odontologo);
    Turno turnoGuardado = turnoService.guardarTurno(turno);

    // Act
    Throwable thrown = catchThrowable(() -> turnoService.buscarTurnoPorId(turnoGuardado.getId() + 1));
    // Assert
    assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
  }

  @Test
  public void testGuardarTurnoConPacienteNull() {
    // Arrange
    Odontologo odontologo = new Odontologo(null, 123456655L, "Hillary", "Villarreal", null);
    odontologoService.guardarOdontologo(odontologo);

    // Creas un turno con odontólogo pero sin paciente
    Turno turno = new Turno(null, LocalDate.parse("2024-06-24"), LocalTime.parse("10:00"), null, odontologo);

    // Act
    Throwable thrown = catchThrowable(() -> turnoService.guardarTurno(turno));

    // Assert
    assertThat(thrown).isInstanceOf(BadRequestException.class);
  }

  @Test
  public void testBuscarTurnoPorIdConIdNulo() {
    // Act
    Throwable thrown = catchThrowable(() -> turnoService.buscarTurnoPorId(null));

    // Assert
    assertThat(thrown).isInstanceOf(InvalidInputException.class);
  }

  @Test
  public void testBuscarTodosTurnos() {
    List<Turno> turnos = turnoService.buscarTodosTurnos();
    assertThat(turnos).isNotNull();
  }

  @Test
  public void testActualizarFechaTurno() {
    // Arrange
    Odontologo odontologo = new Odontologo(null, 111222444L, "Jose", "Gomez", null);
    Paciente paciente = new Paciente(null, 987655777L, "Alex", "Peralta", null, LocalDate.parse("2024-07-01"), null);

    odontologoService.guardarOdontologo(odontologo);
    pacienteService.guardarPaciente(paciente);

    Turno turno = new Turno(null, LocalDate.parse("2023-04-29"), LocalTime.parse("08:00"), paciente, odontologo);
    Turno turnoGuardado = turnoService.guardarTurno(turno);

    // Act
    turnoGuardado.setFecha(LocalDate.parse("2024-06-24"));
    Turno turnoActualizado = turnoService.actualizarTurno(turnoGuardado);

    // Assert
    assertThat(turnoActualizado.getFecha()).isEqualTo(LocalDate.parse("2024-06-24"));
  }

  @Test
  public void testActualizarHoraTurno() {
    // Arrange
    Odontologo odontologo = new Odontologo(null, 111222333L, "Pablo", "Gomez", null);
    Paciente paciente = new Paciente(null, 987655667L, "Luisa", "Peralta", null, LocalDate.parse("2024-05-01"), null);

    odontologoService.guardarOdontologo(odontologo);
    pacienteService.guardarPaciente(paciente);

    Turno turno = new Turno(null, LocalDate.parse("2023-04-29"), LocalTime.parse("08:00"), paciente, odontologo);
    Turno turnoGuardado = turnoService.guardarTurno(turno);

    // Act
    turnoGuardado.setHora(LocalTime.parse("13:00"));
    Turno turnoActualizado = turnoService.actualizarTurno(turnoGuardado);

    // Assert
    assertThat(turnoActualizado.getHora()).isEqualTo(LocalTime.parse("13:00"));
  }

  @Test
  public void testEliminarTurnoPorId() {
    // Arrange
    Odontologo odontologo = new Odontologo(null, 111222333L, "Pablo", "Gomez", null);
    Paciente paciente = new Paciente(null, 987655667L, "Luisa", "Peralta", null, LocalDate.parse("2024-05-01"), null);

    odontologoService.guardarOdontologo(odontologo);
    pacienteService.guardarPaciente(paciente);

    Turno turno = new Turno(null, LocalDate.parse("2024-06-24"), LocalTime.parse("13:00"), paciente, odontologo);
    Turno turnoGuardado = turnoService.guardarTurno(turno);

    // Act
    turnoService.eliminarTurnoPorId(turnoGuardado.getId());
    //Turno turnoEncontrado = turnoService.buscarTurnoPorId(turnoGuardado.getId());
    Throwable thrown = catchThrowable(() -> turnoService.buscarTurnoPorId(turnoGuardado.getId()));

    // Assert
    //assertThat(turnoEncontrado).isNull();
    assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
  }

  @Test
  public void testAsignarTurno() {
    // Arrange
    Odontologo odontologo = new Odontologo(null, 4324234234L, "Pedro", "González", null);
    Paciente paciente = new Paciente(null, 4324234234L, "Luis", "González", null, LocalDate.parse("2023-01-01"), null);
    odontologoService.guardarOdontologo(odontologo);
    pacienteService.guardarPaciente(paciente);

    // Act
    Turno turno = new Turno(null, LocalDate.parse("2023-01-01"), LocalTime.parse("10:00"), paciente, odontologo);
    Turno turnoGuardado = turnoService.guardarTurno(turno);

    // Assert
    assertThat(turnoGuardado).isNotNull();
    assertThat(turnoGuardado.getOdontologo().getNombre()).isEqualTo("Pedro");
    assertThat(turnoGuardado.getPaciente().getNombre()).isEqualTo("Luis");
  }

  @Test
  public void testCrearPacienteAsignarDomicilioYTurno() {
    // Arrange
    Domicilio domicilio = new Domicilio(null, "Calle Z", 999, "Ciudad Z", "Provincia Z");
    Domicilio domicilioGuardado = domicilioService.guardarDomicilio(domicilio);

    Paciente paciente = new Paciente(null, 1234678L, "Lucía", "Reyes", domicilioGuardado, LocalDate.parse("2024-08-01"), null);
    Paciente pacienteGuardado = pacienteService.guardarPaciente(paciente);

    Odontologo odontologo = new Odontologo(null, 98765L, "Raúl", "Pérez", null);
    Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);

    // Act
    Turno turno = new Turno(null, LocalDate.parse("2024-08-01"), LocalTime.parse("10:00"), pacienteGuardado, odontologoGuardado);
    Turno turnoGuardado = turnoService.guardarTurno(turno);

    // Assert
    assertThat(turnoGuardado).isNotNull();
    assertThat(turnoGuardado.getPaciente().getDomicilio().getCalle()).isEqualTo("Calle Z");
    assertThat(turnoGuardado.getOdontologo().getNombre()).isEqualTo("Raúl");
  }
}
