package com.cotilink.Cotilink.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cotilink.Cotilink.Service.CotisationMensuelleService;
import com.cotilink.Cotilink.web.dtos.CotisationMensuelleDTO;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cotisations")
public class CotisationMensuelleController {

    @Autowired
    private CotisationMensuelleService cotisationMensuelleService;

    @PostMapping
    public CotisationMensuelleDTO enregistrerCotisation(@RequestBody CotisationMensuelleDTO dto) {
        return cotisationMensuelleService.enregistrerCotisation(dto);
    }
    // recupérer les cotisations par mois
    @GetMapping("/mois/{moisCotisation}")
    public List<CotisationMensuelleDTO> obtenirCotisationsParMois(@PathVariable YearMonth moisCotisation) {
        return cotisationMensuelleService.getCotisationsByMonth(moisCotisation);
    }
    
    // recupérer les cotisations par membre
    @GetMapping("/membre/{membreId}")
    public List<CotisationMensuelleDTO> obtenirCotisationsParMembre(@PathVariable Long membreId) {
        return cotisationMensuelleService.obtenirCotisationsParMembre(membreId);
    }

    // recupèrer une cotisation par membre et mois
    @GetMapping("/{membreId}/{moisCotisation}")
    public Optional<CotisationMensuelleDTO> obtenirCotisation(@PathVariable Long membreId, @PathVariable YearMonth moisCotisation) {
        return cotisationMensuelleService.obtenirCotisation(membreId, moisCotisation);
    }

    @PostMapping("/valider/{cotisationId}")
    public void validerPaiement(@PathVariable Long cotisationId) {
        cotisationMensuelleService.validerPaiement(cotisationId);
    }

    @GetMapping("/notifications/retard")
    public void envoyerNotificationsRetard() {
        cotisationMensuelleService.envoyerNotificationsRetard();
    }
}
