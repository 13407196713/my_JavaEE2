package service;

import model.Card;
import model.MyUser;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public interface CardService {

	public String addCard(Card card, HttpServletRequest request, String act, HttpSession session) throws IllegalStateException, IOException;
	public String detail(Model model, int id, String act);
	public String delete(int id);
	public String loginOut(Model model, HttpSession session);
	public String toUpdatePwd(Model model, HttpSession session);
	public String updatePwd(MyUser myuser);
	public String selectAllCardsByPage(Model model, int currentPage, String act, HttpSession session);
}
