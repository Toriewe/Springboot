package pe.popeyegym.popeye_gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.popeyegym.popeye_gym.model.Membresia;

@Repository
public interface MembresiaRepository extends JpaRepository<Membresia, Integer> {
}
