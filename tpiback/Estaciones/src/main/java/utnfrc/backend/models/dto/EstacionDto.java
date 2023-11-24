package utnfrc.backend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstacionDto {
    private int id;
    private String nombre;
    private double latitud;
    private double longitud;
    private LocalDateTime fecha_hora_creacion;

}

