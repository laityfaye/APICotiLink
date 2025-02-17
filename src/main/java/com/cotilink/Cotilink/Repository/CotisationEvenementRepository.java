package com.cotilink.Cotilink.Repository;

import com.cotilink.Cotilink.Model.CotisationEvenement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CotisationEvenementRepository extends JpaRepository<CotisationEvenement, Long> {
    List<CotisationEvenement> findByEvenementId(Long evenementId);
    List<CotisationEvenement> findByMembreId(Long membreId);
    boolean existsByEvenementIdAndMembreId(Long evenementId, Long membreId);
    @Query("SELECT c FROM CotisationEvenement c WHERE c.statut = false AND c.evenement.date < CURRENT_DATE")
    List<CotisationEvenement> findCotisationsEnRetard();

}
