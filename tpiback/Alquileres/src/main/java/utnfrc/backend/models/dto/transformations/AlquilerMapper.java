package utnfrc.backend.models.dto.transformations;

import org.springframework.stereotype.Service;
import utnfrc.backend.models.Alquiler;
import utnfrc.backend.models.dto.AlquilerDto;

import java.util.function.Function;

@Service
public class AlquilerMapper implements Function<AlquilerDto, Alquiler> {
    @Override
    public Alquiler apply(AlquilerDto alquilerDto){
        return new Alquiler(
                alquilerDto.getId(),
                alquilerDto.getIdCliente(),
                alquilerDto.getEstado(),
                alquilerDto.getEstacionRetiro(),
                alquilerDto.getEstacionDevolucion(),
                alquilerDto.getFechaHoraRetiro(),
                alquilerDto.getFechaHoraDevolucion(),
                alquilerDto.getMonto(),
                alquilerDto.getTarifa()
        );
    }
}
