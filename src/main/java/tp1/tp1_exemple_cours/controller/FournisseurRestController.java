package tp1.tp1_exemple_cours.controller;



import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp1.tp1_exemple_cours.entity.Fournisseur;
import tp1.tp1_exemple_cours.repository.FournisseurRepository;

import java.util.List;

@RestController
@RequestMapping("/api/fournisseurs")
@RequiredArgsConstructor
public class FournisseurRestController {

    private final FournisseurRepository repo;

    @GetMapping
    public List<Fournisseur> lister() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fournisseur> afficher(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Fournisseur> ajouter(@RequestBody Fournisseur f) {
        f.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(f));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fournisseur> modifier(@PathVariable Long id, @RequestBody Fournisseur f) {
        return repo.findById(id).map(existant -> {
            existant.setNom(f.getNom());
            existant.setContact(f.getContact());
            existant.setAdresse(f.getAdresse());
            return ResponseEntity.ok(repo.save(existant));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
