package com.cotilink.Cotilink.web.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CotisationEvenementDTO(
    Long id,
    Long evenementId,
    Long membreId,
    BigDecimal montant,
    LocalDate datePaiement,
    boolean statut
) {}
