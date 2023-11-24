package utnfrc.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import utnfrc.backend.models.*;
import utnfrc.backend.repository.AlquilerRepository;
import utnfrc.backend.repository.TarifaRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class AlquilerService {

    private final AlquilerRepository repositorio;

    private final TarifaRepository tarifaRepository;

    @Autowired
    public AlquilerService(AlquilerRepository repo, TarifaRepository tarifaRepository) {
        this.repositorio = repo;
        this.tarifaRepository = tarifaRepository;
    }

    public Iterable<Alquiler> getAll() {
        return repositorio.findAll();
    }


    public Alquiler addAlquiler(String idCliente, Long idEstacion){
        // con rest realizamos un get a estaciones y obtenemos la info de la estacion de retiro
        RestTemplate template = new RestTemplate();
        ResponseEntity<Estaciones> res = template.getForEntity("http://localhost:8081/api/estaciones/"+idEstacion, Estaciones.class);
        Estaciones estacion =  res.getBody();

        Alquiler alquiler = new Alquiler();
        alquiler.setFechaHoraRetiro(LocalDateTime.now());
        alquiler.setEstacionRetiro(estacion);
        // establecemos el alquiler en 1(activo)
        alquiler.setEstado(1);
        alquiler.setIdCliente(idCliente);
        repositorio.save(alquiler);
        return alquiler;

    }

    public Alquiler finalizarAlquiler(Long idEstacionDevolucion,String idCliente, String tipoMoneda){
        Alquiler alquiler = repositorio.findByEstadoAndIdCliente(1,idCliente);
        if(alquiler != null) {
            // usamos rest para obtener la estacion de devolucion
            RestTemplate template = new RestTemplate();
            ResponseEntity<Estaciones> res = template.getForEntity("http://localhost:8081/api/estaciones/" + idEstacionDevolucion, Estaciones.class);
            Estaciones estacion = res.getBody();
            alquiler.setEstacionDevolucion(estacion);
            // seteamos el estado de alquiler en 2 finalizado.
            alquiler.setEstado(2);
            alquiler.setFechaHoraDevolucion(java.time.LocalDateTime.now());
            alquiler.setTarifa(determinarTarifa(alquiler));

            alquiler.setMonto(calcularMonto(alquiler));
            if (tipoMoneda != null) {
                double importe = convertirMoneda(tipoMoneda, alquiler.getMonto());
                alquiler.setMonto(importe);
            }
            repositorio.save(alquiler);
            return alquiler;
        }
        else{
            throw new NoSuchElementException("No se encontró un alquiler activo para el cliente.");

        }}


    public double convertirMoneda(String monedaDestino, double importe){
        try {
            // info necesaria para la conversion
            RestTemplate template = new RestTemplate();
            MonedaRequest requestBody = new MonedaRequest(monedaDestino, importe);
            HttpEntity<MonedaRequest> entity = new HttpEntity<>(requestBody);

            // post para convertir el monto a otra moneda
            ResponseEntity<MonedaResponse> res = template.postForEntity(
                    "http://34.82.105.125:8080/convertir", entity, MonedaResponse.class
            );

            if (res.getStatusCode().is2xxSuccessful()){
                //se devuelve el importe convertido
                return Objects.requireNonNull(res.getBody().getImporte());
            } else {
                throw new NoSuchElementException("Error al buscar la moneda");

            }
        } catch (HttpClientErrorException e){
            throw new NoSuchElementException("Error al procesar la moneda");
        }
    }
    public Tarifa determinarTarifa(Alquiler alquiler) {
        LocalDateTime fecha = alquiler.getFechaHoraDevolucion();

        // verificamos si alguna tarifa definida por día, mes y año coincide con la fecha de devolución
        List<Tarifa> tarifas = tarifaRepository.findByDefinicion("C");
        for (Tarifa t : tarifas) {
            if (t.getDiaMes() == fecha.getDayOfMonth() &&
                    t.getMes() == fecha.getMonthValue() &&
                    t.getAnio() == fecha.getYear()) {
                return t;
            }
        }

        // Si no coincide ninguna, entonces buscamos la tarifa correspondiente definida por el día de la semana
        Optional<Tarifa> optionalTarifa = tarifaRepository.findByDiaSemana(fecha.getDayOfWeek().getValue());

        if (optionalTarifa.isPresent()) {
            return optionalTarifa.get();
        }

        throw new NoSuchElementException("No se encontraron tarifas definidas.");
    }

    public double calcularMonto(Alquiler alq) {
        // tarifa asociada al alq
        Tarifa tarifa = alq.getTarifa();
        double monto;

        double lat1 = alq.getEstacionRetiro().getLatitud();
        double lon1 = alq.getEstacionRetiro().getLongitud();
        double lat2 = alq.getEstacionDevolucion().getLatitud();
        double lon2 = alq.getEstacionDevolucion().getLongitud();
        long distanciaEnKilometros = (long) Math.floor(Math.sqrt(Math.pow(lat1 - lat2, 2) + Math.pow(lon1 - lon2, 2)) * 110);
        //calcula la diferencia en minutos entre las fechas y horas de devolución y retiro.
        long diferenciaEnMinutos = Duration.between(alq.getFechaHoraRetiro(), alq.getFechaHoraDevolucion()).toMinutes();

        long horasCompletas = diferenciaEnMinutos / 60;
        long minutosRestantes = diferenciaEnMinutos % 60;

        monto = tarifa.getMontoFijoAlquiler();
        monto += distanciaEnKilometros * tarifa.getMontoKm();
        monto += horasCompletas * tarifa.getMontoHora();

        if (minutosRestantes > 30) {
            monto += tarifa.getMontoHora();
        } else {
            monto += minutosRestantes * tarifa.getMontoMinutoFraccion();
        }

        return monto;
    }
}
