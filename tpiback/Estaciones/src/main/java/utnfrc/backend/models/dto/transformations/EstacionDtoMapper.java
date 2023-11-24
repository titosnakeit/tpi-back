package utnfrc.backend.models.dto.transformations;

import org.springframework.stereotype.Service;
import utnfrc.backend.models.Estacion;
import utnfrc.backend.models.dto.EstacionDto;

import java.util.function.Function;

@Service
public class EstacionDtoMapper implements Function<Estacion, EstacionDto> {
    @Override
    public EstacionDto apply(Estacion estacion){
        return new EstacionDto(
                estacion.getId(),
                estacion.getNombre(),
                estacion.getLatitud(),
                estacion.getLongitud(),
                estacion.getFecha_hora_creacion()

        );
    }
}
