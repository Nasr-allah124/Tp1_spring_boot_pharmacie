package tp1.tp1_exemple_cours.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import tp1.tp1_exemple_cours.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> { }