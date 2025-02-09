package com.cotilink.Cotilink.Repository;

import com.cotilink.Cotilink.Model.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface EvenementRepository extends JpaRepository<Evenement, Long> {

    // Vérifie si un événement existe déjà avec les mêmes nom, date et heure
    Optional<Evenement> findByNomAndDateAndHeure(String nom, LocalDate date, LocalTime heure);
}
