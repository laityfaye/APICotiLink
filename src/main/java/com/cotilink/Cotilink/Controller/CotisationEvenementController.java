package com.cotilink.Cotilink.Controller;

import com.cotilink.Cotilink.Service.CotisationEvenementService;
import com.cotilink.Cotilink.web.dtos.CotisationEvenementDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cotisations/evenements")
@RequiredArgsConstructor
public class CotisationEvenementController {

    private final CotisationEvenementService service;

    // ✅ Ajouter une cotisation
    @PostMapping
    public ResponseEntity<CotisationEvenementDTO> addCotisation(@RequestBody CotisationEvenementDTO dto) {
        return ResponseEntity.ok(service.addCotisation(dto));
    }

    // ✅ Lister toutes les cotisations
    @GetMapping
    public ResponseEntity<List<CotisationEvenementDTO>> getAllCotisations() {
        return ResponseEntity.ok(service.getAllCotisations());
    }

    // ✅ Obtenir une cotisation par son ID
    @GetMapping("/{id}")
    public ResponseEntity<CotisationEvenementDTO> getCotisationById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getCotisationById(id));
    }

    // ✅ Lister les cotisations pour un événement spécifique
    @GetMapping("/evenement/{evenementId}")
    public ResponseEntity<List<CotisationEvenementDTO>> getCotisationsByEvenement(@PathVariable Long evenementId) {
        return ResponseEntity.ok(service.getCotisationsByEvenement(evenementId));
    }

    // ✅ Lister les cotisations pour un membre spécifique
    @GetMapping("/membre/{membreId}")
    public ResponseEntity<List<CotisationEvenementDTO>> getCotisationsByMembre(@PathVariable Long membreId) {
        return ResponseEntity.ok(service.getCotisationsByMembre(membreId));
    }

    // ✅ Mettre à jour une cotisation
    @PutMapping("/{id}")
    public ResponseEntity<CotisationEvenementDTO> updateCotisation(@PathVariable Long id, @RequestBody CotisationEvenementDTO dto) {
        return ResponseEntity.ok(service.updateCotisation(id, dto));
    }

    // ✅ Supprimer une cotisation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCotisation(@PathVariable Long id) {
        service.deleteCotisation(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Envoyer des notifications
    @GetMapping("/notifications/retard")
    public void envoyerNotificationsRetard(){
        service.envoyerNotificationsRetard();
    }

    // ✅ valider une cotisation évémentiel 
    @GetMapping("/valider/{id}")
    public void validerPaiement(@PathVariable Long id){
        service.validerPaiement(id);
    }
}
