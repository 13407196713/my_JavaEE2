package controller;

import model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping("/toLogin")
	public String toLogin(@ModelAttribute MyUser myUser) {
		return "login";
	}

	@RequestMapping("/login")
	public String login(@ModelAttribute MyUser myUser, Model model, HttpSession session) {
		return userService.login(myUser, model, session);
	}

	@RequestMapping("/toRegister")
	public String toRegister(@ModelAttribute("myUser") MyUser myUser) {
		return "register";
	}

	@RequestMapping("/register")
	public String register(@ModelAttribute("myUser") MyUser myUser, Model model) {
		return userService.register(myUser);
	}

	@RequestMapping("/checkUname")
	@ResponseBody
	public String checkUname(@RequestBody MyUser myUser) {
		return userService.checkUname(myUser);
	}

}
