package tp1.tp1_exemple_cours.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import tp1.tp1_exemple_cours.entity.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long> { }