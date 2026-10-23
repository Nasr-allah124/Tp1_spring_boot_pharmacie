package tp1.tp1_exemple_cours.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tp1.tp1_exemple_cours.entity.Adresse;

@Repository
public interface AdresseRepository extends JpaRepository<Adresse, Long> {
}
