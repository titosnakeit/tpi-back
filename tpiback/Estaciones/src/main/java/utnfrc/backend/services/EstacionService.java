package utnfrc.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utnfrc.backend.models.Estacion;
import utnfrc.backend.models.dto.EstacionDto;
import utnfrc.backend.repository.EstacionRepository;
import utnfrc.backend.models.dto.transformations.EstacionDtoMapper;
import utnfrc.backend.models.dto.transformations.EstacionMapper;

import java.util.Iterator;
import java.util.Optional;

@Service
public class EstacionService {

    private final EstacionRepository repositorio;
    private final EstacionDtoMapper estacionDtoMapper;
    private final EstacionMapper estacionMapper;

    @Autowired
    public EstacionService(EstacionRepository repositorio, EstacionDtoMapper estacionDtoMapper, EstacionMapper estacionMapper) {
        this.repositorio = repositorio;
        this.estacionDtoMapper = estacionDtoMapper;
        this.estacionMapper = estacionMapper;
    }

    public Iterable<Estacion> getAll() {
        return repositorio.findAll();
    }

    public Estacion getById(Long id ) {
        Estacion estacion = repositorio.findById(id).orElseThrow(() -> new RuntimeException("No se encontro la estacion"));
        return estacion;

    }

    public Estacion postEstacion(Estacion estacion){return repositorio.save(estacion);}

    public Estacion getEstacionDistancia(double lat, double lon) throws ServiceException{
        try{
            return buscarEstacionCerca(lat, lon);
        }
        catch (ServiceException e) {
            throw e;
        }
    }

    public EstacionDto delete(Long id) {
        Optional<Estacion> optionalCategory = repositorio.findById(id);
        optionalCategory.ifPresent(repositorio::delete);
        return optionalCategory
                .map(estacionDtoMapper)
                .orElseThrow();
    }

    private Estacion buscarEstacionCerca(double latitud, double longitud) throws ServiceException
    {
        Iterable<Estacion> estaciones = repositorio.findAll();

        Iterator<Estacion> iterador = estaciones.iterator();

        if (!(iterador.hasNext())) {throw new ServiceException("no hay estaciones");}

        //----------------------------------------------------------------------------

        Estacion estacion;

        Estacion estacionCerca = null;

        double distanciaCorta = 0.0;


        while (iterador.hasNext())
        {
            estacion = iterador.next();

            if (estacionCerca == null)
            {
                estacionCerca = estacion;
                distanciaCorta = calcularDistancia(latitud, longitud, estacionCerca);
            }
            else
            {
                double distanciaNueva = calcularDistancia(latitud, longitud, estacion);
                if (distanciaCorta > distanciaNueva)
                {
                    distanciaCorta = distanciaNueva;
                    estacionCerca = estacion;
                }
            }
        }
        return estacionCerca;
    }

    private double calcularDistancia(double lat, double longi, Estacion estacion){
        return Math.sqrt(Math.pow((lat * -1.0 + estacion.getLatitud()), 2.0) + Math.pow((longi * -1.0 + estacion.getLongitud()), 2.0));
    }
}
