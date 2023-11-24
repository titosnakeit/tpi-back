package utnfrc.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Entity(name = "estaciones")
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Estaciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Column(name = "FECHA_HORA_CREACION")
    private LocalDate fechaHoraCreacion;
    private Double latitud;
    private Double longitud;
}
