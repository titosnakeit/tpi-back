package utnfrc.backend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utnfrc.backend.models.Estaciones;
import utnfrc.backend.models.Tarifa;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlquilerDto {
    private long id;
    private String idCliente;
    private int estado;
    private Estaciones estacionRetiro;
    private Estaciones estacionDevolucion;
    private LocalDateTime fechaHoraRetiro;
    private LocalDateTime fechaHoraDevolucion;
    private double monto;
    private Tarifa tarifa;
}
