package com.cotilink.Cotilink.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import com.cotilink.Cotilink.web.dtos.CotisationMensuelleDTO;

public interface CotisationMensuelleService {

    List<CotisationMensuelleDTO> getCotisationsByMonth(YearMonth moisCotisation);

    CotisationMensuelleDTO enregistrerCotisation(CotisationMensuelleDTO dto);

    List<CotisationMensuelleDTO> obtenirCotisationsParMembre(Long membreId);

    Optional<CotisationMensuelleDTO> obtenirCotisation(Long membreId, YearMonth moisCotisation);

    void envoyerNotificationsRetard();

    void validerPaiement(Long cotisationId);
}
