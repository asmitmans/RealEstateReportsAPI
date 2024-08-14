package cl.fullstackjava.reportesInmobiliaria.model.repository;

import cl.fullstackjava.reportesInmobiliaria.model.entities.RealEstateProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RealEstateProjectRepository extends JpaRepository<RealEstateProject, Integer> {
}
