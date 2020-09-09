package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import web.model.Role;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;
import java.util.Set;


@Repository
public class UserDaoImpl implements UserDao {

    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private RoleDao roleDao;

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    /*private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setBCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }*/

    @Override
    @SuppressWarnings("unchecked")
    public List<User> allUsers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<User> userList = entityManager.createQuery("SELECT u FROM User u").getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return userList;
    }

    @Override
    public User findById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = entityManager.find(User.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = entityManager.find(User.class, id);
        if (user != null){
            entityManager.remove(user);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public User findByUserForNickname(String nickname) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = null;
        try {
            Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.nickname=:nickname");
            query.setParameter("nickname", nickname);
            user = (User) query.getSingleResult();
        } catch (Exception e) {

        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return user;
    }

    @Override
    public boolean saveUser(User user) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        User userFromDB = findByUserForNickname(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        Set<Role> roleSet = (Set<Role>) user.getAuthorities();
        user.setRoles(roleSet);

        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
        return true;

    }

    @Override
    public List<User> usergtList(Long idMin) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<User> list = entityManager.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return list;
    }


    @Override
    public boolean edit(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        //Set<Role> roleSet = (Set<Role>) user.getAuthorities();
        //user.setRoles(roleSet);
        //user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        entityManager.merge(user);
        entityManager.getTransaction().commit();
        entityManager.close();
        return true;
    }
}
