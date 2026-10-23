package tp1.tp1_exemple_cours.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tp1.tp1_exemple_cours.entity.Adresse;
import tp1.tp1_exemple_cours.entity.Personne;
import tp1.tp1_exemple_cours.repository.AdresseRepository;
import tp1.tp1_exemple_cours.repository.PersonneRepository;

import java.util.List;

@RestController
public class PersonneRestController {

    @Autowired
    private PersonneRepository personneRepository;

    @Autowired
    private AdresseRepository adresseRepository;

    @GetMapping("/personnes")
    public List<Personne> getPersonnes() {
        return personneRepository.findAll();
    }

    @GetMapping("/personnes/{id}")
    public Personne getPersonne(@PathVariable("id") long id) {
        return personneRepository.findById(id).orElse(null);
    }

    @PostMapping("/personnes")
    public Personne addPersonne(@RequestBody Personne personne) {
        List<Adresse> adresses = personne.getAdresses();

        for (Adresse adresse : adresses) {
            Adresse adr = null;
            if (adresse.getId() != null) {
                adr = adresseRepository.findById(adresse.getId()).orElse(null);
                adresses.set(adresses.indexOf(adresse), adr);
            } else {
                adr = adresseRepository.save(adresse);
            }
        }

        return personneRepository.saveAndFlush(personne);
    }

    @PutMapping("/personnes/{id}")
    public Personne modifierPersonnes(@PathVariable("id") long id, @RequestBody Personne personne1) {
        return personneRepository.findById(id).map(personne -> {
            personne.setNom(personne1.getNom());
            personne.setPrenom(personne1.getPrenom());
            return personneRepository.save(personne);
        }).orElseGet(() -> {
            personne1.setNum(id);
            return personneRepository.save(personne1);
        });
    }

    @DeleteMapping("/personnes/{id}")
    public void deletePersonne(@PathVariable("id") long id) {
        personneRepository.deleteById(id);
    }
}