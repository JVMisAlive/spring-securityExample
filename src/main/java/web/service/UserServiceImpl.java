package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.User;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        User user = findUserByNickname(nickname);
        if (user == null) {
            System.err.println("User not found");
        }
        return user;
    }

    @Transactional
    public List<User> allUsers() {
        return userDao.allUsers();
    }

    @Transactional
    public User findById(Long id) {
        return userDao.findById(id);
    }


    @Transactional
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Transactional
    public boolean saveUser(User user) {
        return userDao.saveUser(user);
    }

    @Override
    @Transactional
    public boolean edit(User user) {
        return userDao.edit(user);
    }

    @Override
    @Transactional
    public User findUserByNickname(String nickname) {
        return userDao.findByUserForNickname(nickname);
    }

}
