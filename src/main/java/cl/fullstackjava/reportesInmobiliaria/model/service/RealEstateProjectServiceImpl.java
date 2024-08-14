package cl.fullstackjava.reportesInmobiliaria.model.service;

import cl.fullstackjava.reportesInmobiliaria.model.entities.RealEstateProject;
import cl.fullstackjava.reportesInmobiliaria.model.repository.RealEstateProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RealEstateProjectServiceImpl implements RealEstateProjectService {

    private final RealEstateProjectRepository realEstateProjectRepository;

    public RealEstateProjectServiceImpl(RealEstateProjectRepository realEstateProjectRepository) {
        this.realEstateProjectRepository = realEstateProjectRepository;
    }

    @Override
    public List<RealEstateProject> findAll() {
        return realEstateProjectRepository.findAll();
    }

    @Override
    public RealEstateProject findOne(int id) throws EntityNotFoundException {
        return realEstateProjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + id));
    }

    @Override
    public RealEstateProject create(RealEstateProject r) {
        r.setId(0);
        return realEstateProjectRepository.save(r);
    }

    @Override
    public RealEstateProject update(RealEstateProject r) throws EntityNotFoundException {
        if (realEstateProjectRepository.existsById(r.getId())) {
            return realEstateProjectRepository.save(r);
        } else  {
            throw  new EntityNotFoundException("Project with ID " + r.getId() + " not found.");
        }
    }

    @Override
    public void delete(int id) throws EntityNotFoundException {
        if (realEstateProjectRepository.existsById(id)) {
            realEstateProjectRepository.deleteById(id);
        } else  {
            throw new EntityNotFoundException("Project with ID " + id + " not found.");
        }
    }

}
