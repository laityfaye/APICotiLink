package com.cotilink.Cotilink.web.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public record EvenementDTO(
    Long id,
    String nom,
    String description,
    LocalDate date,
    LocalTime heure,
    String lieu,
    String type, // "COLLECTIF" ou "PERSONNEL"
    String statut, // "À VENIR", "TERMINÉ", "ANNULÉ"
    String organisateur,
    Long membreId // ID du membre organisateur (null si événement collectif)
) {}
