package web.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import web.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> allUsers();

    User findById(Long id);

    void deleteUser(Long id);

    boolean saveUser(User user);

    boolean edit(User user);

    User findUserByNickname(String nickname);

}
