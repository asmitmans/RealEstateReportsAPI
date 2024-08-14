package cl.fullstackjava.reportesInmobiliaria.model.service;

import cl.fullstackjava.reportesInmobiliaria.model.entities.Role;
import cl.fullstackjava.reportesInmobiliaria.model.entities.User;
import cl.fullstackjava.reportesInmobiliaria.model.repository.RoleRepository;
import cl.fullstackjava.reportesInmobiliaria.model.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findOne(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public boolean create(User u) {
        User existingUser = userRepository.findByUsername(u.getUsername());
        Optional<User> optionalUser = Optional.ofNullable(existingUser);
        if (optionalUser.isPresent()) {
            throw new IllegalArgumentException("Email is already registered.");
        }

        String encodedPassword = passwordEncoder.encode(u.getPassword());
        u.setPassword(encodedPassword);

        if (u.getRoles() == null || u.getRoles().isEmpty()) {
            Role defaultRole = roleRepository.findByName("ROLE_USER");
            if (defaultRole != null) {
                u.setRoles(List.of(defaultRole));
            } else {
                throw new RuntimeException("Default role 'ROLE_USER' not found.");
            }
        }

        try {
            userRepository.save(u);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(User u) {
        if (userRepository.existsById(u.getId())) {
            userRepository.save(u);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}