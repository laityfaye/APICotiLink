package com.cotilink.Cotilink.Impl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.cotilink.Cotilink.Model.Membre;
import com.cotilink.Cotilink.Repository.MembreRepository;
import com.cotilink.Cotilink.Service.MembreService;
import com.cotilink.Cotilink.web.dtos.MembreDTO;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MembreServiceImpls implements MembreService {

   private final MembreRepository membreRepository;
   private final BCryptPasswordEncoder passwordEncoder;

   public MembreServiceImpls(MembreRepository membreRepository, BCryptPasswordEncoder passwordEncoder) {
       this.membreRepository = membreRepository;
       this.passwordEncoder = passwordEncoder;
   }

   // Conversion entité -> DTO
   private MembreDTO toDTO(Membre membre) {
       return new MembreDTO(
               membre.getId(),
               membre.getPrenom(),
               membre.getNom(),
               membre.getAddresse(),
               membre.getDateIntegration(),
               membre.getTelephone(),
               membre.getPassword(),
               membre.getRole(),
               membre.getDisabled()
       );
   }

   // Conversion DTO -> entité
   private Membre toEntity(MembreDTO dto) {
       Membre membre = new Membre();
       // Ne pas setter l'ID pour la création
       if (dto.id() != null) {
           membre.setId(dto.id());
       }
       membre.setPrenom(dto.prenom());
       membre.setNom(dto.nom());
       membre.setAddresse(dto.addresse());
       membre.setDateIntegration(dto.dateIntegration());
       membre.setTelephone(dto.telephone());
       membre.setPassword(dto.password());
       membre.setRole(dto.role());
       membre.setDisabled(dto.disabled() != null ? dto.disabled() : false);
       return membre;
   }

   @Override
   public List<MembreDTO> getAllMembres() {
       log.info("Récupération de tous les membres");
       List<MembreDTO> membres = membreRepository.findAll()
               .stream()
               .map(this::toDTO)
               .collect(Collectors.toList());
       log.info("Nombre de membres trouvés : {}", membres.size());
       return membres;
   }

   @Override
   public MembreDTO AddMembre(MembreDTO dto) {
       log.info("Création d'un nouveau membre: {}", dto);
       if (dto.id() != null) {
           log.error("Tentative de création d'un membre avec un ID existant");
           throw new IllegalArgumentException("L'ID ne doit pas être fourni lors de la création");
       }
       // Vérifier si le numéro de téléphone existe déjà
       if (membreRepository.findByTelephone(dto.telephone()) != null) {
           log.error("Tentative de création d'un membre avec un téléphone existant");
           throw new IllegalArgumentException("Le numéro de téléphone est déjà utilisé.");
       }
       Membre membre = toEntity(dto);
       membre.setPassword(passwordEncoder.encode(dto.password()));
       Membre savedMembre = membreRepository.save(membre);
       log.info("Membre créé avec succès: {}", savedMembre);
       return toDTO(savedMembre);
   }



   @Override
   public MembreDTO getMembreById(Long id) {
       log.info("Recherche du membre avec l'ID: {}", id);
       Membre membre = membreRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Aucun membre trouvé avec l'ID: " + id));
       return toDTO(membre);
   }

   @Override
   public Membre updateMembre(Long id, MembreDTO dto) {
       log.info("Mise à jour du membre avec l'ID: {}", id);
       Membre membre = membreRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Aucun membre trouvé avec l'ID: " + id));
       
       membre.setPrenom(dto.prenom());
       membre.setNom(dto.nom());
       membre.setAddresse(dto.addresse());
       membre.setDateIntegration(dto.dateIntegration());
       membre.setTelephone(dto.telephone());
       // Mettre à jour le mot de passe uniquement si un nouveau mot de passe est fourni
       if (dto.password() != null && !dto.password().isEmpty()) {
           membre.setPassword(passwordEncoder.encode(dto.password()));
       }
       membre.setRole(dto.role());
       membre.setDisabled(dto.disabled() != null ? dto.disabled() : membre.getDisabled());

       Membre updatedMembre = membreRepository.save(membre);
       log.info("Membre mis à jour avec succès: {}", updatedMembre);
       return updatedMembre;
   }

//    @Override
//    public void deleteMembre(Long id) {
//        log.info("Suppression du membre avec l'ID: {}", id);
//        if (!membreRepository.existsById(id)) {
//            log.error("Aucun membre trouvé avec l'ID: {}", id);
//            throw new IllegalArgumentException("Aucun membre trouvé avec l'ID: " + id);
//        }
//        membreRepository.deleteById(id);
//        log.info("Membre supprimé avec succès");
//     }
   
   
}