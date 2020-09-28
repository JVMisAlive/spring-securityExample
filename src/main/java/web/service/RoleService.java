package web.service;

import web.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    void saveRole(Role role);

    void deleteRole(Role role);

    void updateRole(Role role);

    Role getRoleById(Long id);

    List<Role> getAllRoles();

    Role getRoleByName(String name);

    Set<Role> getViewRole(String[] view);
}
