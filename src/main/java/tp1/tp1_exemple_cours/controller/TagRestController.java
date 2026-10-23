package tp1.tp1_exemple_cours.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp1.tp1_exemple_cours.entity.Tag;
import tp1.tp1_exemple_cours.repository.TagRepository;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagRestController {

    private final TagRepository repo;

    @GetMapping
    public List<Tag> lister() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> afficher(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tag> ajouter(@RequestBody Tag t) {
        t.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(t));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tag> modifier(@PathVariable Long id, @RequestBody Tag t) {
        return repo.findById(id).map(existant -> {
            existant.setLibelle(t.getLibelle());
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
