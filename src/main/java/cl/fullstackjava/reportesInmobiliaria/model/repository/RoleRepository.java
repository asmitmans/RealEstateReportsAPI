package cl.fullstackjava.reportesInmobiliaria.model.repository;

import cl.fullstackjava.reportesInmobiliaria.model.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
