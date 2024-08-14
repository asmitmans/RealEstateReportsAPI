package cl.fullstackjava.reportesInmobiliaria.model.service;

import cl.fullstackjava.reportesInmobiliaria.model.entities.RealEstateProject;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface RealEstateProjectService {
    List<RealEstateProject> findAll();
    RealEstateProject findOne(int id) throws EntityNotFoundException;
    RealEstateProject create(RealEstateProject r);
    RealEstateProject update(RealEstateProject r) throws EntityNotFoundException;
    void delete(int id) throws EntityNotFoundException;
}

