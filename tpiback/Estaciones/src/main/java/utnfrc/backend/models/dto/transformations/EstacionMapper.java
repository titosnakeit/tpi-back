package utnfrc.backend.models.dto.transformations;

import org.springframework.stereotype.Service;
import utnfrc.backend.models.Estacion;
import utnfrc.backend.models.dto.EstacionDto;
import java.util.function.Function;

@Service
public class EstacionMapper implements Function<EstacionDto, Estacion> {
    @Override
    public Estacion apply(EstacionDto estacionDto){
        return new Estacion(
                estacionDto.getId(),
                estacionDto.getNombre(),
                estacionDto.getLatitud(),
                estacionDto.getLongitud(),
                estacionDto.getFecha_hora_creacion()
        );
    }
}
