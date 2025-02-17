package com.cotilink.Cotilink.Model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "cotisations_evenement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CotisationEvenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evenement_id", nullable = false)
    private Evenement evenement;

    @ManyToOne
    @JoinColumn(name = "membre_id", nullable = false)
    private Membre membre;

    @Column(nullable = false)
    private BigDecimal montant;

    @Column(nullable = false)
    private LocalDate datePaiement;

    @Column(nullable = false)
    private boolean statut; 
}
