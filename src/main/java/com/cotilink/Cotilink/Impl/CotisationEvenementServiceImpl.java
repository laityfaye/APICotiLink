package com.cotilink.Cotilink.Impl;
import com.cotilink.Cotilink.Model.CotisationEvenement;
import com.cotilink.Cotilink.Model.Evenement;
import com.cotilink.Cotilink.Model.Membre;
import com.cotilink.Cotilink.Repository.CotisationEvenementRepository;
import com.cotilink.Cotilink.Repository.EvenementRepository;
import com.cotilink.Cotilink.Repository.MembreRepository;
import com.cotilink.Cotilink.Service.CotisationEvenementService;
import com.cotilink.Cotilink.web.dtos.CotisationEvenementDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CotisationEvenementServiceImpl implements CotisationEvenementService {

    private final CotisationEvenementRepository cotisationRepository;
    private final EvenementRepository evenementRepository;
    private final MembreRepository membreRepository;

    @Override
    public CotisationEvenementDTO addCotisation(CotisationEvenementDTO dto) {
        if (cotisationRepository.existsByEvenementIdAndMembreId(dto.evenementId(), dto.membreId())) {
            throw new IllegalStateException("Ce membre a déjà payé pour cet événement.");
        }

        Evenement evenement = evenementRepository.findById(dto.evenementId())
                .orElseThrow(() -> new RuntimeException("Événement non trouvé"));
        Membre membre = membreRepository.findById(dto.membreId())
                .orElseThrow(() -> new RuntimeException("Membre non trouvé"));

        CotisationEvenement cotisation = CotisationEvenement.builder()
                .evenement(evenement)
                .membre(membre)
                .montant(dto.montant())
                .datePaiement(dto.datePaiement())
                .statut(false)
                .build();

        cotisation = cotisationRepository.save(cotisation);
        return mapToDTO(cotisation);
    }

    @Override
    public List<CotisationEvenementDTO> getAllCotisations() {
        return cotisationRepository.findAll()
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public CotisationEvenementDTO getCotisationById(Long id) {
        CotisationEvenement cotisation = cotisationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cotisation non trouvée"));
        return mapToDTO(cotisation);
    }

    @Override
    public List<CotisationEvenementDTO> getCotisationsByEvenement(Long evenementId) {
        return cotisationRepository.findByEvenementId(evenementId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<CotisationEvenementDTO> getCotisationsByMembre(Long membreId) {
        return cotisationRepository.findByMembreId(membreId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public CotisationEvenementDTO updateCotisation(Long id, CotisationEvenementDTO dto) {
        CotisationEvenement cotisation = cotisationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cotisation non trouvée"));

        cotisation.setMontant(dto.montant());
        cotisation.setDatePaiement(dto.datePaiement());
        cotisation.setStatut(dto.statut());

        return mapToDTO(cotisationRepository.save(cotisation));
    }

    @Override
    public void envoyerNotificationsRetard() {
        // Logique de notification (ex: envoi d'email ou SMS)
        List<CotisationEvenement> cotisationsNonPayees = cotisationRepository.findCotisationsEnRetard()
                .stream()
                .filter(c -> !c.isStatut() && c.getEvenement().getDate().isBefore(java.time.LocalDate.now()))
                .collect(Collectors.toList());

        cotisationsNonPayees.forEach(cotisation ->
                System.out.println("Notification envoyée au membre : " + cotisation.getMembre().getId())
        );
    }

    @Override
    public void validerPaiement(Long cotisationId) {
        CotisationEvenement cotisation = cotisationRepository.findById(cotisationId)
                .orElseThrow(() -> new IllegalArgumentException("Cotisation introuvable !"));
        cotisation.setStatut(true);
        cotisationRepository.save(cotisation);
    }

    @Override
    public void deleteCotisation(Long id) {
        cotisationRepository.deleteById(id);
    }

    private CotisationEvenementDTO mapToDTO(CotisationEvenement cotisation) {
        return new CotisationEvenementDTO(
                cotisation.getId(),
                cotisation.getEvenement().getId(),
                cotisation.getMembre().getId(),
                cotisation.getMontant(),
                cotisation.getDatePaiement(),
                cotisation.isStatut()
        );
    }


}
