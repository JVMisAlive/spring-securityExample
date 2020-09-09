package web.service;

import web.model.Role;

import java.util.List;

public interface RoleService {
    void saveRole(Role role);

    void deleteRole(Role role);

    void updateRole(Role role);

    Role getRoleById(int id);

    List<Role> getAllRoles();

    Role getRoleByName(String name);
}
