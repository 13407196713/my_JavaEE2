package service;

import dao.UserDao;
import model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import po.MyUserTable;
import util.MD5Util;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;

	//检查用户名是否可用
	@Override
	public String checkUname(MyUser myUser) {
		List<MyUserTable> userList = userDao.selectByUname(myUser);
		if(userList.size() > 0)
			return "no";
		//System.out.println("asd");
		return "ok";
	}

	//实现注册功能
	@Override
	public String register(MyUser myUser) {
		//将明文变成密文
		myUser.setUpwd(MD5Util.MD5(myUser.getUpwd()));
		if(userDao.register(myUser) > 0)
			return "login";
		return "register";
	}

	//实现登录功能
	@Override
	public String login(MyUser myUser, Model model, HttpSession session) {
		//ValidateCodeController中的rand
		String code = (String)session.getAttribute("rand");
		if(!code.equalsIgnoreCase(myUser.getCode())) {
			model.addAttribute("errorMessage", "验证码错误！");
			return "login";
		}else {
			//将明文变成密文
			myUser.setUpwd(MD5Util.MD5(myUser.getUpwd()));
			List<MyUserTable> list = userDao.login(myUser);
			if(list.size() > 0){
				session.setAttribute("userLogin", list.get(0));
				return "redirect:/card/selectAllCardsByPage?currentPage=1&act=select";
			}else {
				model.addAttribute("errorMessage", "用户名或密码错误！");
				return "login";
			}
		}
	}
}
