import com.odontologia.project.ProjectApplication;


import com.odontologia.project.models.Turno;
import com.odontologia.project.services.impl.TurnoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest(classes = ProjectApplication.class)
@Transactional
@Rollback
public class TurnoServiceTest {

    @Autowired
    private TurnoService turnoService;

    @Test
    public void testGuardarTurno() {
        Turno turno = new Turno(null,LocalDate.parse("2024-06-24"), LocalTime.parse("10:00"),null,null);
        Turno turnoGuardado = turnoService.guardarTurno(turno);
        assertThat(turnoGuardado).isNotNull();
        assertThat(turnoGuardado.getId()).isNotNull();
    }

    @Test
    public void testBuscarTurnoPorId() {
        Turno turno = new Turno(null, LocalDate.parse("2023-04-29"), LocalTime.parse("08:00"), null, null);
        Turno turnoGuardado = turnoService.guardarTurno(turno);
        Turno turnoEncontrado = turnoService.buscarTurnoPorId(turnoGuardado.getId());
        assertThat(turnoEncontrado).isNotNull();
    }

    @Test
    public void testBuscarTodosTurnos() {
        List<Turno> turnos = turnoService.buscarTodosTurnos();
        assertThat(turnos).isNotNull();
    }

    @Test
    public void testActualizarTurno() {
        Turno turno = new Turno(null, LocalDate.parse("2023-04-29"), LocalTime.parse("08:00"), null, null);
        Turno turnoGuardado = turnoService.guardarTurno(turno);
        turnoGuardado.setFecha(LocalDate.parse("2024-06-24"));
        turnoGuardado.setHora(LocalTime.parse("13:00"));
        Turno turnoActualizado = turnoService.actualizarTurno(turnoGuardado);
        assertThat(turnoActualizado.getFecha()).isEqualTo(LocalDate.parse("2024-06-24"));
        assertThat(turnoActualizado.getHora()).isEqualTo(LocalTime.parse("13:00"));
    }

    @Test
    public void testEliminarTurnoPorId() {
        Turno turno = new Turno(null, LocalDate.parse("2024-06-24"), LocalTime.parse("13:00"), null, null);
        Turno turnoGuardado = turnoService.guardarTurno(turno);
        turnoService.eliminarTurnoPorId(turnoGuardado.getId());
        Turno turnoEncontrado = turnoService.buscarTurnoPorId(turnoGuardado.getId());
        assertThat(turnoEncontrado).isNull();
    }

}
