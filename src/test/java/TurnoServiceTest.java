import com.odontologia.project.ProjectApplication;

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

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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
    Turno turno = new Turno(null,LocalDate.parse("2024-06-24"), LocalTime.parse("10:00"),null,null);

    // Act
    Turno turnoGuardado = turnoService.guardarTurno(turno);

    // Assert
    assertThat(turnoGuardado).isNotNull();
  }

  @Test
  public void testBuscarTurnoPorId() {
    // Arrange
    Turno turno = new Turno(null, LocalDate.parse("2023-04-29"), LocalTime.parse("08:00"), null, null);
    Turno turnoGuardado = turnoService.guardarTurno(turno);

    // Act
    Turno turnoEncontrado = turnoService.buscarTurnoPorId(turnoGuardado.getId());

    // Assert
    assertThat(turnoEncontrado).isNotNull();
  }

  @Test
  public void testBuscarTodosTurnos() {
    List<Turno> turnos = turnoService.buscarTodosTurnos();
    assertThat(turnos).isNotNull();
  }

  @Test
  public void testActualizarFechaTurno() {
    // Arrange
    Turno turno = new Turno(null, LocalDate.parse("2023-04-29"), LocalTime.parse("08:00"), null, null);
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
    Turno turno = new Turno(null, LocalDate.parse("2023-04-29"), LocalTime.parse("08:00"), null, null);
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
    Turno turno = new Turno(null, LocalDate.parse("2024-06-24"), LocalTime.parse("13:00"), null, null);
    Turno turnoGuardado = turnoService.guardarTurno(turno);

    // Act
    turnoService.eliminarTurnoPorId(turnoGuardado.getId());
    Turno turnoEncontrado = turnoService.buscarTurnoPorId(turnoGuardado.getId());

    // Assert
    assertThat(turnoEncontrado).isNull();
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

    Odontologo odontologo = new Odontologo(null, 98765L, "Raúl", "Pérez",null);
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
