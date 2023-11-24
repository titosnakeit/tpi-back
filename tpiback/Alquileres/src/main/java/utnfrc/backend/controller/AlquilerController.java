package utnfrc.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utnfrc.backend.models.Alquiler;
import utnfrc.backend.services.AlquilerService;

@Slf4j
@RestController
@RequestMapping("/api/alquileres")
public class AlquilerController {

    private final AlquilerService servicio;

    @Autowired
    public AlquilerController(AlquilerService serv){this.servicio = serv;}
    @GetMapping
    public ResponseEntity<Iterable<Alquiler>> getAllAlquileres() {
        return ResponseEntity.ok(servicio.getAll());
    }

    @PostMapping("/add/{idEstacion}/{idCliente}")
    //mal pathvariable
    public ResponseEntity<Alquiler> addAlquiler(@PathVariable("idEstacion") Long idEstacion, @PathVariable("idCliente") String idCliente){
        Alquiler alquiler = servicio.addAlquiler(idCliente,idEstacion);
        return ResponseEntity.ok(alquiler);
    }

    @PatchMapping("/finalizar")
    public ResponseEntity<Alquiler> finalizarAlquiler(
            @RequestParam("idEstacion") Long idEstacion,
            @RequestParam("idCliente") String idCliente,
            @RequestParam(value = "tipoMoneda",required = false) String tipoMoneda){
        Alquiler alquiler = servicio.finalizarAlquiler(idEstacion,idCliente,tipoMoneda);
        return ResponseEntity.ok(alquiler);
    }

}
