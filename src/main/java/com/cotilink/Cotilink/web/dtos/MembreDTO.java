package com.cotilink.Cotilink.web.dtos; 
import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MembreDTO(
    Long id,
    @NotBlank(message = "Le prénom est obligatoire")
    String prenom,
    @NotBlank(message = "Le nom est obligatoire")
    String nom,
    String addresse,
    @NotNull(message = "La date d'intégration est obligatoire")
    LocalDate dateIntegration,
    String telephone,
    @NotBlank(message = "Le mot de passe est obligatoire")
    String password,
    @NotBlank(message = "Le rôle est obligatoire")
    String role,
    Boolean disabled
) {}
