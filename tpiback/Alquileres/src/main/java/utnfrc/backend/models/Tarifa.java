package utnfrc.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "tarifas")
@NoArgsConstructor
@AllArgsConstructor
public class Tarifa {
    @Id
    @GeneratedValue(generator = "tarifas", strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "tipo_tarifa")
    private int tipoTarifa;

    private String definicion;

    @Column(name = "dia_semana")
    private Integer diaSemana;

    @Column(name = "dia_mes")
    private Integer diaMes;

    private Integer mes;

    private Integer anio;

    @Column(name = "monto_fijo_alquiler")
    private double montoFijoAlquiler;

    @Column(name = "monto_minuto_fraccion")
    private double montoMinutoFraccion;

    @Column(name = "monto_km")
    private double montoKm;

    @Column(name = "monto_hora")
    private double montoHora;
}