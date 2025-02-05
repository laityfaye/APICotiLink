package com.cotilink.Cotilink.Model;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "membre")
public class Membre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "mbr_id")
    private Long id;

    @Column(name = "prenom", nullable = false, length = 250) 
    private String prenom;

    @Column(name = "nom", nullable = false, length = 250) 
    private String nom;

    @Column(name = "addresse", nullable = true, length = 250)
    private String addresse;

    @Column(name = "date_integration", nullable = true)
    private LocalDate dateIntegration;

    @Column(name = "telephone", unique = true, nullable = false, length = 250)
    private String telephone;

    @Column(name ="password", nullable = false, length = 250)
    private String password;

    @Column(name = "role", nullable = false, length = 250)
    private String role;

    @Column(name = "mbr_disabled", nullable = false, columnDefinition = "boolean default false")
    private Boolean disabled = false; 
}