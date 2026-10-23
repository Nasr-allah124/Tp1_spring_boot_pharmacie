package tp1.tp1_exemple_cours.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tp1.tp1_exemple_cours.entity.Personne;

@Repository
public interface PersonneRepository extends JpaRepository<Personne, Long> {
}
