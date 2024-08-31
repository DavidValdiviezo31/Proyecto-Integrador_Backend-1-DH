import com.odontologia.project.ProjectApplication;
import com.odontologia.project.models.Domicilio;
import com.odontologia.project.services.impl.DomicilioService;
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
public class DomicilioServiceTest {

    @Autowired
    private DomicilioService domicilioService;

    @Test
    public void testGuardarDomicilio() {
        Domicilio domicilio = new Domicilio(null,"Calle P sherman",524,"Medellín","Antioquia");
        Domicilio domicilioGuardado = domicilioService.guardarDomicilio(domicilio);

        assertThat(domicilioGuardado).isNotNull();
        assertThat(domicilioGuardado.getId()).isNotNull();
    }

    @Test
    void testBuscarDomicilioPorId() {
        Domicilio domicilio = new Domicilio(null,"Calle P sherman", 524, "Medellín", "Antioquia");
        Domicilio domicilioGuardado = domicilioService.guardarDomicilio(domicilio);
        Domicilio domicilioEncontrado = domicilioService.buscarDomicilioPorId(domicilioGuardado.getId());
        assertThat(domicilioEncontrado).isNotNull();
    }

    @Test
    void testBuscarTodosDomicilios() {
        List<Domicilio> domicilios = domicilioService.buscarTodosDomicilios();
        assertThat(domicilios).isNotNull();
    }

    @Test
    void testActualizarDomicilio() {
        Domicilio domicilio = new Domicilio(null,"Calle P sherman", 524, "Medellín", "Antioquia");
        Domicilio domicilioGuardado = domicilioService.guardarDomicilio(domicilio);
        domicilioGuardado.setCalle("Calle Juan23");
        domicilioGuardado.setNumero(456);
        Domicilio domicilioActualizado = domicilioService.actualizarDomicilio(domicilioGuardado);
        assertThat(domicilioActualizado.getCalle()).isEqualTo("Calle Juan23");
        assertThat(domicilioActualizado.getNumero()).isEqualTo(456);
    }

    @Test
    void testEliminarDomicilioPorId() {
        Domicilio domicilio = new Domicilio(null,"Calle Juan23", 366, "B", "USA");
        Domicilio domicilioGuardado = domicilioService.guardarDomicilio(domicilio);
        domicilioService.eliminarDomicilioPorId(domicilioGuardado.getId());
        Domicilio domicilioEncontrado = domicilioService.buscarDomicilioPorId(domicilioGuardado.getId());
        assertThat(domicilioEncontrado).isNull();
    }
}
