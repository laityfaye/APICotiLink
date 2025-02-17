package com.cotilink.Cotilink.Service;

import com.cotilink.Cotilink.web.dtos.CotisationEvenementDTO;

import java.util.List;

public interface CotisationEvenementService {
    CotisationEvenementDTO addCotisation(CotisationEvenementDTO dto);
    List<CotisationEvenementDTO> getAllCotisations();
    CotisationEvenementDTO getCotisationById(Long id);
    List<CotisationEvenementDTO> getCotisationsByEvenement(Long evenementId);
    List<CotisationEvenementDTO> getCotisationsByMembre(Long membreId);
    CotisationEvenementDTO updateCotisation(Long id, CotisationEvenementDTO dto);
    void deleteCotisation(Long id);
    void envoyerNotificationsRetard();
    void validerPaiement(Long cotisationId);
}
