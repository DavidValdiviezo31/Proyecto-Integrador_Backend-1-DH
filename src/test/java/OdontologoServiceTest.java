import com.odontologia.project.ProjectApplication;
import com.odontologia.project.models.Odontologo;
import com.odontologia.project.services.impl.OdontologoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest(classes = ProjectApplication.class)
@Transactional
@Rollback
public class OdontologoServiceTest {

    @Autowired
    private OdontologoService odontologoService;

    @Test
    public void testGuardarOdontologo() {
        Odontologo odontologo = new Odontologo( null, 1214714065L,"Juan","Gonzalez",null);
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);
        assertThat(odontologoGuardado).isNotNull();
        assertThat(odontologoGuardado.getId()).isNotNull();
    }

    @Test
    public void testBuscarOdontologoPorId() {
        Odontologo odontologo = new Odontologo( null, 1214714065L,"Juan","Gonzalez",null);
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);
        Odontologo odontologoEncontrado = odontologoService.buscarOdontologoPorId(odontologoGuardado.getId());
        assertThat(odontologoEncontrado).isNotNull();
    }

    @Test
    public void testBuscarTodosOdontologos() {
        List<Odontologo> odontologos = odontologoService.buscarTodosOdontologos();
        assertThat(odontologos).isNotNull();
    }

    @Test
    public void testActualizarOdontologo() {
        Odontologo odontologo = new Odontologo( null, 1214714065L,"Juan","Gonzalez",null);
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);
        odontologoGuardado.setNombre("Carlos");
        odontologoGuardado.setApellido("Velez");
        Odontologo odontologoActualizado = odontologoService.actualizarOdontologo(odontologoGuardado);
        assertThat(odontologoActualizado.getNombre()).isEqualTo("Carlos");
        assertThat(odontologoActualizado.getApellido()).isEqualTo("Velez");
    }

    @Test
    public void testEliminarOdontologoPorId() {
        Odontologo odontologo = new Odontologo( null, 1214714065L,"Carlos","Velez",null);
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);
        odontologoService.eliminarOdontologoPorId(odontologoGuardado.getId());
        Odontologo odontologoEncontrado = odontologoService.buscarOdontologoPorId(odontologoGuardado.getId());
        assertThat(odontologoEncontrado).isNull();
    }
}

