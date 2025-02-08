package com.cotilink.Cotilink.Service;

import java.util.List;

import com.cotilink.Cotilink.Model.Membre;
import com.cotilink.Cotilink.web.dtos.MembreDTO;

public interface MembreService {
    List<MembreDTO> getAllMembres();
    MembreDTO AddMembre(MembreDTO dto);
    MembreDTO getMembreById(Long id);
    Membre updateMembre(Long id, MembreDTO dto);
    // void deleteMembre(Long id);

}
