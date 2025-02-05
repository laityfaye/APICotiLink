package com.cotilink.Cotilink.Controller;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cotilink.Cotilink.Model.Membre;
import com.cotilink.Cotilink.Service.MembreService;
import com.cotilink.Cotilink.web.dtos.MembreDTO;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/membres")
@Slf4j
public class MembreController {

    private final MembreService membreService;

    public MembreController(MembreService membreService) {
        this.membreService = membreService;
    }

    @GetMapping
    public ResponseEntity<List<MembreDTO>> getAllMembres() {
        List<MembreDTO> membres = membreService.getAllMembres();
        return ResponseEntity.ok(membres);
    }

    @PostMapping
    public ResponseEntity<MembreDTO> createMembre(@RequestBody MembreDTO membreDTO) {
        MembreDTO savedMembre = membreService.AddMembre(membreDTO);
        return ResponseEntity.ok(savedMembre);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembreDTO> getMembreById(@PathVariable Long id) {
        MembreDTO membre = membreService.getMembreById(id);
        return ResponseEntity.ok(membre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Membre> updateMembre(@PathVariable Long id, @RequestBody MembreDTO membreDTO) {
        Membre updatedMembre = membreService.updateMembre(id, membreDTO);
        return ResponseEntity.ok(updatedMembre);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMembre(@PathVariable Long id) {
        membreService.deleteMembre(id);
        return ResponseEntity.noContent().build();
    }
}
