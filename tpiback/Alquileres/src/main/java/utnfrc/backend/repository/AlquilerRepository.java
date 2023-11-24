package utnfrc.backend.repository;

import org.springframework.data.repository.CrudRepository;
import utnfrc.backend.models.Alquiler;

public interface AlquilerRepository extends CrudRepository<Alquiler, Long> {
    Alquiler findByEstadoAndIdCliente(Integer estado, String idCliente);
}
