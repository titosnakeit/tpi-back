package utnfrc.backend.models;

import utnfrc.backend.models.Estaciones;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "alquileres")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alquiler {
    @Id
    @GeneratedValue(generator = "alquileres", strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "id_cliente")
    private String idCliente;

    private int estado;

    @ManyToOne
    @JoinColumn(name = "estacion_retiro")
    private Estaciones estacionRetiro;

    @ManyToOne
    @JoinColumn(name = "estacion_devolucion")
    private Estaciones estacionDevolucion;

    @Column(name = "fecha_hora_retiro")
    private LocalDateTime fechaHoraRetiro;

    @Column(name = "fecha_hora_devolucion")
    private LocalDateTime fechaHoraDevolucion;

    @Column(name = "monto")
    private double monto;

    @OneToOne
    @JoinColumn(name = "id_tarifa")
    private Tarifa tarifa;

}
