package com.cotilink.Cotilink.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.cotilink.Cotilink.Model.Membre;

public interface MembreRepository extends JpaRepository<Membre, Long> {
   Membre findByTelephone(String telephone);
}
