package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Role;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    private EntityManager entityManager ;

    @Override
    public void addRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public void deleteRole(Role role) {
        entityManager.remove(entityManager.contains(role) ? role : entityManager.merge(role));
    }

    @Override
    public void updateRole(Role role) {
        entityManager.merge(role);
    }

    @Override
    public Role getRoleById(Long id) {
       Query query = entityManager.createQuery("SELECT r FROM Role r WHERE r.id = ?1");
        query.setParameter(1, id);
        return (Role) query.getSingleResult();
    }

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }

    @Override
    public Role getRoleByName(String name) {
        Query query = entityManager.createQuery("SELECT r FROM Role r WHERE r.name = ?1");
        query.setParameter(1, name);
        return (Role) query.getSingleResult();
    }

    @Override
    public Set<Role> getViewRole(String[] view) {
        Set<Role> roles = new HashSet<>();
        for(String str: view){
            roles.add(getRoleByName(str));
        }
        return roles;
    }
}
