package tp1.tp1_exemple_cours.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnoreProperties("tags")
    private List<Produit> produits = new ArrayList<>();

    public Tag() {}

    public Tag(String libelle) {
        this.libelle = libelle;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public List<Produit> getProduits() { return produits; }
    public void setProduits(List<Produit> produits) { this.produits = produits; }
}