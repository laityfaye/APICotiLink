package com.cotilink.Cotilink.Service;

import com.cotilink.Cotilink.web.dtos.EvenementDTO;

import java.util.List;

public interface EvenementService {
    List<EvenementDTO> getAllEvenements();
    EvenementDTO addEvenement(EvenementDTO dto);
    EvenementDTO getEvenementById(Long id);
    EvenementDTO updateEvenement(Long id, EvenementDTO dto);
    void deleteEvenement(Long id);
}
