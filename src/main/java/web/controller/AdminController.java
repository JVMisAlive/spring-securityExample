package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "login";
    }

    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "users";
    }

    @GetMapping("/admin/add")
    public String addUser(Model model) {
        model.addAttribute("addUser", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "userAdd";
    }

    @PostMapping("/admin/add")
    public String addUser(@ModelAttribute("addUser") @Valid User userForm,
                          @RequestParam(value = "addRole", required = false) String userRole,
                          BindingResult bindingResult, Model model) {
        model.addAttribute("allRoles", roleService.getAllRoles());
        if (bindingResult.hasErrors()) {
            return "userAdd";
        }
        if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "userAdd";
        }

        Set<Role> roles = new HashSet<>();

        if (userRole.contains("USER")) {
            roles.add(new Role(1L, "USER"));
            userForm.setRoles(roles);
        } else if (userRole.contains("ADMIN")) {
            roles.add(new Role(2L, "ADMIN"));
            userForm.setRoles(roles);
        }
        userService.saveUser(userForm);

        return "redirect:/admin";
    }

    @GetMapping("/admin/edit/{id}")
    public ModelAndView editPage(@PathVariable("id") Long id){
        User user = userService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userEdit");
        modelAndView.addObject("userEdit", user);
        modelAndView.addObject("allRoles", roleService.getAllRoles());
        return modelAndView;
    }
    @PostMapping("/admin/edit")
    public ModelAndView editUser(@ModelAttribute("user") User user){
        user.setRoles(userService.findById(user.getId()).getRoles());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin");
        userService.saveUser(user);
        return modelAndView;
    }

    /*@GetMapping("/admin/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("userEdit", user);
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "userEdit";
    }

    @PostMapping("/admin/edit/{id}")
    public String editUser(@ModelAttribute("userEdit") @Valid User user,
                           @RequestParam(value = "editRole") String editRole,
                           BindingResult bindingResult, Model model) {
        model.addAttribute("allRoles", roleService.getAllRoles());
        if (bindingResult.hasErrors()) {
            return "userEdit";
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "userEdit";
        }
        user.setRoles(userService.findById(user.getId()).getRoles());
        Set<Role> roleSet = new HashSet<>();

        if (editRole.contains("USER")){
            roleSet.add(new Role(1L, "USER"));
            user.setRoles(roleSet);
        }
        else if (editRole.contains("ADMIN")) {
            roleSet.add(new Role(2L, "ADMIN"));
            user.setRoles(roleSet);
        }

        userService.edit(user);
        return "redirect:/admin";
    }*/

    @GetMapping("/admin/gt/{userId}")
    public String gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userService.usergtList(userId));
        return "users";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
