package utnfrc.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity(name = "estaciones")
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Estacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;
    @Column(name = "LATITUD", nullable = false)
    private double latitud;
    @Column(name = "LONGITUD", nullable = false)
    private double longitud;
    @Column(name = "FECHA_HORA_CREACION")
    private LocalDateTime fecha_hora_creacion;

}