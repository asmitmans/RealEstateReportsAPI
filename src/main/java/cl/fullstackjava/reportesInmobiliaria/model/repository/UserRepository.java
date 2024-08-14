package cl.fullstackjava.reportesInmobiliaria.model.repository;

import cl.fullstackjava.reportesInmobiliaria.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
