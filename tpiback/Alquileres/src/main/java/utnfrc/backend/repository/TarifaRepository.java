package utnfrc.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utnfrc.backend.models.Tarifa;

import java.util.List;
import java.util.Optional;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {

    List<Tarifa> findByDefinicion(String definicion);

    Optional<Tarifa> findByDiaSemana(Integer diaSemana);
}