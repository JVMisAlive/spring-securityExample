package web.dao;

import web.model.User;
import java.util.List;


public interface UserDao {

    List<User> allUsers();

    User findById(Long id);

    void deleteUser(Long id);

    boolean saveUser(User user);

    boolean edit(User user);

    User findByUserForNickname(String nickname);
}
