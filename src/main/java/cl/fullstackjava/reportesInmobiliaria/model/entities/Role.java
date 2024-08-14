package cl.fullstackjava.reportesInmobiliaria.model.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="t_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int roleId;
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToMany(mappedBy = "roles")
    List<User> users = new ArrayList<>();

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", name='" + name + '\'' +
                '}';
    }

    public Role() {
    }

    public Role(int roleId, String name, List<User> users) {
        this.roleId = roleId;
        this.name = name;
        this.users = users;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
