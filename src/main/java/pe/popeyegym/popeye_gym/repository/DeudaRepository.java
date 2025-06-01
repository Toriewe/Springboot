package pe.popeyegym.popeye_gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.popeyegym.popeye_gym.model.Deuda;

import java.util.Optional;

@Repository
public interface DeudaRepository extends JpaRepository<Deuda, Integer> {
    Optional<Deuda> findByClienteClienteid(Integer clienteId);
}
