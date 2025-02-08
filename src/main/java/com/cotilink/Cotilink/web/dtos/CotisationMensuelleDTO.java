package com.cotilink.Cotilink.web.dtos;

import java.time.LocalDate;
import java.time.YearMonth;

public record CotisationMensuelleDTO(
    Long id,
    Long membreId,
    Double montant,
    LocalDate datePaiement,
    boolean statutPaiement,
    YearMonth moisCotisation
) {}
