package utnfrc.backend.models.dto.transformations;

import org.springframework.stereotype.Service;
import utnfrc.backend.models.Alquiler;
import utnfrc.backend.models.dto.AlquilerDto;

import java.util.function.Function;

@Service
public class AlquilerDtoMapper implements Function<Alquiler, AlquilerDto> {
    @Override
    public AlquilerDto apply(Alquiler alquiler){
        return new AlquilerDto(
                alquiler.getId(),
                alquiler.getIdCliente(),
                alquiler.getEstado(),
                alquiler.getEstacionRetiro(),
                alquiler.getEstacionDevolucion(),
                alquiler.getFechaHoraRetiro(),
                alquiler.getFechaHoraDevolucion(),
                alquiler.getMonto(),
                alquiler.getTarifa()
        );
    }
}
