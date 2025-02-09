package com.cotilink.Cotilink.Controller;

import com.cotilink.Cotilink.Service.EvenementService;
import com.cotilink.Cotilink.web.dtos.EvenementDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evenements")
@RequiredArgsConstructor
public class EvenementController {

    private final EvenementService evenementService;

    @GetMapping
    public ResponseEntity<List<EvenementDTO>> getAllEvenements() {
        List<EvenementDTO> evenements = evenementService.getAllEvenements();
        return ResponseEntity.ok(evenements);
    }

    @PostMapping("/creer")
    public ResponseEntity<EvenementDTO> createEvenement(@RequestBody EvenementDTO evenementDTO) {
        EvenementDTO createdEvenement = evenementService.addEvenement(evenementDTO);
        return new ResponseEntity<>(createdEvenement, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvenementDTO> getEvenementById(@PathVariable Long id) {
        EvenementDTO evenement = evenementService.getEvenementById(id);
        return ResponseEntity.ok(evenement);
    }

    @PutMapping("/modifier/{id}")
    public ResponseEntity<EvenementDTO> updateEvenement(@PathVariable Long id, @RequestBody EvenementDTO evenementDTO) {
        EvenementDTO updatedEvenement = evenementService.updateEvenement(id, evenementDTO);
        return ResponseEntity.ok(updatedEvenement);
    }

    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Void> deleteEvenement(@PathVariable Long id) {
        evenementService.deleteEvenement(id);
        return ResponseEntity.noContent().build();
    }
}
