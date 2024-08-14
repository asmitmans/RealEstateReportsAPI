package cl.fullstackjava.reportesInmobiliaria.model.service;

import cl.fullstackjava.reportesInmobiliaria.model.entities.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    Role findOne(int id);
    boolean create(Role r);
    boolean update(Role r);
    boolean delete(int id);
}
