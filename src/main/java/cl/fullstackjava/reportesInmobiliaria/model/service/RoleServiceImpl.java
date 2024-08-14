package cl.fullstackjava.reportesInmobiliaria.model.service;

import cl.fullstackjava.reportesInmobiliaria.model.entities.Role;
import cl.fullstackjava.reportesInmobiliaria.model.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findOne(int id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public boolean create(Role r) {
        try {
            roleRepository.save(r);
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(Role r) {
        if (roleRepository.existsById(r.getRoleId())) {
            roleRepository.save(r);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
            return true;
        }
        return false;
    }
}