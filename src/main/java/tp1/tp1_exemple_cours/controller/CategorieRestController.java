package tp1.tp1_exemple_cours.controller;



import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp1.tp1_exemple_cours.entity.Categorie;
import tp1.tp1_exemple_cours.repository.CategorieRepository;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategorieRestController {

    private final CategorieRepository repo;

    @GetMapping
    public List<Categorie> lister() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categorie> afficher(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Categorie> ajouter(@RequestBody Categorie c) {
        c.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(c));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categorie> modifier(@PathVariable Long id, @RequestBody Categorie c) {
        return repo.findById(id).map(existant -> {
            existant.setNom(c.getNom());
            existant.setDescription(c.getDescription());
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
