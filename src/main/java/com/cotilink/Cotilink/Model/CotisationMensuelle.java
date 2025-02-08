package com.cotilink.Cotilink.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.YearMonth;

@Entity
@Table(name = "cotisations_mensuelles", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"membre_id", "mois_cotisation"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CotisationMensuelle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "membre_id", nullable = false, foreignKey = @ForeignKey(name = "fk_cotisation_membre", foreignKeyDefinition = "FOREIGN KEY (membre_id) REFERENCES membre(mbr_id) ON DELETE CASCADE"))
    private Membre membre;

    @Column(nullable = false)
    private Double montant;

    @Column(nullable = false)
    private LocalDate datePaiement;

    @Column(nullable = false)
    private boolean statutPaiement;  

    @Column(nullable = false)
    private YearMonth moisCotisation;  
}
