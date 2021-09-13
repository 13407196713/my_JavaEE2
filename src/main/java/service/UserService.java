package service;

import model.MyUser;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public interface UserService {
	public String checkUname(MyUser myUser);
	public String register(MyUser myUser);
	public String login(MyUser myUser, Model model, HttpSession session);
}
