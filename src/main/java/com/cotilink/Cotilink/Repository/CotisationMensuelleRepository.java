package com.cotilink.Cotilink.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cotilink.Cotilink.Model.CotisationMensuelle;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Repository
public interface CotisationMensuelleRepository extends JpaRepository<CotisationMensuelle, Long> {

    // Récupérer toutes les cotisations d’un membre
    List<CotisationMensuelle> findByMembreId(Long membreId);

    // Récupérer toutes les cotisations d’un mois donné
    List<CotisationMensuelle> findByMoisCotisation(YearMonth moisCotisation);

    // Vérifier si une cotisation existe déjà pour un membre et un mois donné
    boolean existsByMembreIdAndMoisCotisation(Long membreId, YearMonth moisCotisation);

    // Trouver une cotisation spécifique
    Optional<CotisationMensuelle> findByMembreIdAndMoisCotisation(Long membreId, YearMonth moisCotisation);
}
