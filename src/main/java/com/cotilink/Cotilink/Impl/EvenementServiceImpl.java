package com.cotilink.Cotilink.Impl;

import com.cotilink.Cotilink.Model.Evenement;
import com.cotilink.Cotilink.Model.Membre;
import com.cotilink.Cotilink.Repository.EvenementRepository;
import com.cotilink.Cotilink.Service.EvenementService;
import com.cotilink.Cotilink.web.dtos.EvenementDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EvenementServiceImpl implements EvenementService {

    private final EvenementRepository evenementRepository;

    public EvenementServiceImpl(EvenementRepository evenementRepository) {
        this.evenementRepository = evenementRepository;
    }

    // Conversion de l'entité Evenement en DTO
    private EvenementDTO toDTO(Evenement evenement) {
        return new EvenementDTO(
                evenement.getId(),
                evenement.getNom(),
                evenement.getDescription(),
                evenement.getDate(),
                evenement.getHeure(),
                evenement.getLieu(),
                evenement.getType(),
                evenement.getStatut(),
                evenement.getOrganisateur(),
                evenement.getMembre() != null ? evenement.getMembre().getId() : null // Retourne l'ID du membre ou null
        );
    }

    // Conversion du DTO en entité Evenement
    private Evenement toEntity(EvenementDTO dto) {
        Evenement evenement = Evenement.builder()
                .nom(dto.nom())
                .description(dto.description())
                .date(dto.date())
                .heure(dto.heure())
                .lieu(dto.lieu())
                .type(dto.type())
                .statut(dto.statut())
                .organisateur(dto.organisateur())
                .build();

        if (dto.membreId() != null) {
            Membre membre = new Membre(); // Assumer que tu as une méthode pour obtenir un membre par ID
            membre.setId(dto.membreId());
            evenement.setMembre(membre);
        }
        return evenement;
    }

    @Override
    public List<EvenementDTO> getAllEvenements() {
        log.info("Récupération de tous les événements");
        return evenementRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EvenementDTO addEvenement(EvenementDTO dto) {
        log.info("Création d'un nouvel événement: {}", dto);

        // Vérifier si l'événement existe déjà
        Optional<Evenement> existingEvenement = evenementRepository.findByNomAndDateAndHeure(dto.nom(), dto.date(), dto.heure());
        if (existingEvenement.isPresent()) {
            log.warn("Un événement avec ce nom, date et heure existe déjà.");
            throw new IllegalArgumentException("Un événement avec ce nom, date et heure existe déjà.");
        }

        Evenement evenement = toEntity(dto);
        Evenement savedEvenement = evenementRepository.save(evenement);
        log.info("Événement créé avec succès: {}", savedEvenement);
        return toDTO(savedEvenement);
    }

    @Override
    public EvenementDTO getEvenementById(Long id) {
        log.info("Recherche de l'événement avec l'ID: {}", id);
        Evenement evenement = evenementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aucun événement trouvé avec l'ID: " + id));
        return toDTO(evenement);
    }

    @Override
    public EvenementDTO updateEvenement(Long id, EvenementDTO dto) {
        log.info("Mise à jour de l'événement avec l'ID: {}", id);
        Evenement evenement = evenementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aucun événement trouvé avec l'ID: " + id));

        // Vérifier si l'événement avec le même nom, date et heure existe déjà
        Optional<Evenement> existingEvenement = evenementRepository.findByNomAndDateAndHeure(dto.nom(), dto.date(), dto.heure());
        if (existingEvenement.isPresent() && !existingEvenement.get().getId().equals(id)) {
            log.warn("Un événement avec ce nom, date et heure existe déjà.");
            throw new IllegalArgumentException("Un événement avec ce nom, date et heure existe déjà.");
        }

        evenement.setNom(dto.nom());
        evenement.setDescription(dto.description());
        evenement.setDate(dto.date());
        evenement.setHeure(dto.heure());
        evenement.setLieu(dto.lieu());
        evenement.setType(dto.type());
        evenement.setStatut(dto.statut());
        evenement.setOrganisateur(dto.organisateur());

        if (dto.membreId() != null) {
            Membre membre = new Membre(); // Assumer que tu as une méthode pour obtenir un membre par ID
            membre.setId(dto.membreId());
            evenement.setMembre(membre);
        }

        Evenement updatedEvenement = evenementRepository.save(evenement);
        log.info("Événement mis à jour avec succès: {}", updatedEvenement);
        return toDTO(updatedEvenement);
    }

    @Override
    public void deleteEvenement(Long id) {
        log.info("Suppression de l'événement avec l'ID: {}", id);
        if (!evenementRepository.existsById(id)) {
            log.error("Aucun événement trouvé avec l'ID: {}", id);
            throw new IllegalArgumentException("Aucun événement trouvé avec l'ID: " + id);
        }
        evenementRepository.deleteById(id);
    }
}
