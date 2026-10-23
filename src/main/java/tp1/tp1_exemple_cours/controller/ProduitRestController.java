package tp1.tp1_exemple_cours.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tp1.tp1_exemple_cours.entity.*;
import tp1.tp1_exemple_cours.repository.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/produits")
@RequiredArgsConstructor
public class ProduitRestController {

    private final ProduitRepository produitRepo;
    private final CategorieRepository categorieRepo;
    private final FournisseurRepository fournisseurRepo;
    private final TagRepository tagRepo;

    @GetMapping
    public List<Produit> lister() {
        return produitRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produit> afficher(@PathVariable Long id) {
        return produitRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Produit> ajouter(@RequestBody Produit p) {
        p.setId(null);
        resoudreRelations(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(produitRepo.save(p));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produit> modifier(@PathVariable Long id, @RequestBody Produit p) {
        return produitRepo.findById(id).map(existant -> {
            resoudreRelations(p);
            existant.setNom(p.getNom());
            existant.setDescription(p.getDescription());
            existant.setPrix(p.getPrix());
            existant.setCodeBarre(p.getCodeBarre());
            existant.setDateExpiration(p.getDateExpiration());
            existant.setCategorie(p.getCategorie());
            existant.setFournisseur(p.getFournisseur());
            existant.setTags(p.getTags());

            DetailProduit nouveau = p.getDetailProduit();
            if (nouveau != null) {
                DetailProduit d = existant.getDetailProduit();
                if (d == null) {
                    existant.setDetailProduit(nouveau);
                } else {
                    d.setFabricant(nouveau.getFabricant());
                    d.setPaysOrigine(nouveau.getPaysOrigine());
                    d.setComposition(nouveau.getComposition());
                    d.setDosage(nouveau.getDosage());
                    d.setIndications(nouveau.getIndications());
                }
            }
            return ResponseEntity.ok(produitRepo.save(existant));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        if (!produitRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        produitRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Le JSON ne contient que {"id": 5} pour categorie/fournisseur/tags :
    // on recharge les vrais objets depuis la base.
    private void resoudreRelations(Produit p) {
        if (p.getCategorie() != null) {
            p.setCategorie(categorieRepo.findById(p.getCategorie().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Catégorie introuvable")));
        }
        if (p.getFournisseur() != null) {
            p.setFournisseur(fournisseurRepo.findById(p.getFournisseur().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fournisseur introuvable")));
        }
        List<Tag> tags = new ArrayList<>();

        if (p.getTags() != null) {
            for (Tag t : p.getTags()) {
                tags.add(tagRepo.findById(t.getId())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "Tag introuvable : " + t.getId()
                        )));
            }
        }

        p.setTags(tags);
    }
}
