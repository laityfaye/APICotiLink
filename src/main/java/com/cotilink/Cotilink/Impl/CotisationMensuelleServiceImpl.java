package com.cotilink.Cotilink.Impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cotilink.Cotilink.Model.CotisationMensuelle;
import com.cotilink.Cotilink.Repository.CotisationMensuelleRepository;
import com.cotilink.Cotilink.Repository.MembreRepository;
import com.cotilink.Cotilink.Service.CotisationMensuelleService;
import com.cotilink.Cotilink.web.dtos.CotisationMensuelleDTO;


import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CotisationMensuelleServiceImpl implements CotisationMensuelleService {

    private final CotisationMensuelleRepository cotisationMensuelleRepository;
    private final MembreRepository membreRepository;

    public CotisationMensuelleServiceImpl(CotisationMensuelleRepository cotisationMensuelleRepository,
                                          MembreRepository membreRepository) {
        this.cotisationMensuelleRepository = cotisationMensuelleRepository;
        this.membreRepository = membreRepository;
    }

    @Override
    public List<CotisationMensuelleDTO> getCotisationsByMonth(YearMonth moisCotisation) {
        return cotisationMensuelleRepository.findByMoisCotisation(moisCotisation)
                .stream()
                .map(CotisationMensuelleServiceImpl::convertirEnDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CotisationMensuelleDTO enregistrerCotisation(CotisationMensuelleDTO dto) {
        if (cotisationMensuelleRepository.existsByMembreIdAndMoisCotisation(dto.membreId(), dto.moisCotisation())) {
            throw new IllegalArgumentException("Le membre a déjà cotisé pour ce mois !");
        }

        CotisationMensuelle cotisation = new CotisationMensuelle();
        cotisation.setMembre(membreRepository.findById(dto.membreId())
                .orElseThrow(() -> new RuntimeException("Membre introuvable !")));
        cotisation.setMontant(dto.montant());
        cotisation.setDatePaiement(LocalDate.now());
        cotisation.setStatutPaiement(false);
        cotisation.setMoisCotisation(dto.moisCotisation());

        cotisation = cotisationMensuelleRepository.save(cotisation);
        return convertirEnDTO(cotisation);
    }

    @Override
    public List<CotisationMensuelleDTO> obtenirCotisationsParMembre(Long membreId) {
        return cotisationMensuelleRepository.findByMembreId(membreId)
                .stream()
                .map(CotisationMensuelleServiceImpl::convertirEnDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CotisationMensuelleDTO> obtenirCotisation(Long membreId, YearMonth moisCotisation) {
        return cotisationMensuelleRepository.findByMembreIdAndMoisCotisation(membreId, moisCotisation)
                .map(CotisationMensuelleServiceImpl::convertirEnDTO);
    }

    @Override
    @Transactional
    public void validerPaiement(Long cotisationId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            throw new IllegalArgumentException("Vous n'avez pas accès à cette ressource.");
        }
    
        CotisationMensuelle cotisation = cotisationMensuelleRepository.findById(cotisationId)
                .orElseThrow(() -> new IllegalArgumentException("Cotisation introuvable !"));
        cotisation.setStatutPaiement(true);
        cotisationMensuelleRepository.save(cotisation);
    }
    
    
    @Override
    public void envoyerNotificationsRetard() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            throw new IllegalArgumentException("Vous n'avez pas accès à cette ressource.");
        }
    
        List<CotisationMensuelle> cotisationsNonPayees = cotisationMensuelleRepository.findAll()
                .stream()
                .filter(c -> !c.isStatutPaiement() && c.getMoisCotisation().isBefore(YearMonth.now()))
                .collect(Collectors.toList());
    
        // Logique de notification (ex: envoi d'email ou SMS)
        cotisationsNonPayees.forEach(cotisation -> 
            System.out.println("Notification envoyée au membre : " + cotisation.getMembre().getId())
        );
    }
    
    

    private static CotisationMensuelleDTO convertirEnDTO(CotisationMensuelle cotisation) {
        return new CotisationMensuelleDTO(
                cotisation.getId(),
                cotisation.getMembre().getId(),
                cotisation.getMontant(),
                cotisation.getDatePaiement(),
                cotisation.isStatutPaiement(),
                cotisation.getMoisCotisation()
        );
    }
}
