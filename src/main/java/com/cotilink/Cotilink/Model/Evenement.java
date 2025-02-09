package com.cotilink.Cotilink.Model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "evenements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false, length = 255)
    private String nom;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "heure")
    private LocalTime heure;

    @Column(name = "lieu", length = 250)
    private String lieu;

    @Column(name = "type", nullable = false, length = 50)
    private String type;  // "COLLECTIF" ou "PERSONNEL"

    @Column(name = "statut", nullable = false, length = 50)
    private String statut; // "À VENIR", "TERMINÉ", "ANNULÉ"

    @Column(name = "organisateur", length = 250)
    private String organisateur;

    // Relation conditionnelle
    @ManyToOne
    @JoinColumn(name = "id_membre", nullable = true)  // Nullable pour les événements collectifs
    private Membre membre;

    @PrePersist
    public void prePersist() {
        // Si l'événement est collectif, on s'assure qu'il n'y a pas de membre spécifié
        if ("COLLECTIF".equals(this.type)) {
            this.membre = null;  // Pas besoin de membre
        }
    }
}
