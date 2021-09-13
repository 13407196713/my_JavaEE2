package service;

import dao.CardDao;
import model.Card;
import model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import po.CardTable;
import po.MyUserTable;
import util.MD5Util;
import util.MyUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class CardServiceImpl implements CardService{
	@Autowired
	private CardDao cardDao;

	//查询、修改查询、删除查询、分页查询
	@Override
	public String selectAllCardsByPage(Model model, int currentPage, String act, HttpSession session) {
		MyUserTable mut = (MyUserTable)session.getAttribute("userLogin");
		List<Map<String, Object>> allUser = cardDao.selectAllCards(mut.getId());
	    //共多少个用户
	  	int totalCount = allUser.size();

	  	//计算共多少页
	  	int pageSize = 5;

	  	int totalPage = (int)Math.ceil(totalCount*1.0/pageSize);
	  	List<Map<String, Object>> cardsByPage = cardDao.selectAllCardsByPage((currentPage-1)*pageSize, pageSize, mut.getId());
	  	model.addAttribute("act", act);
	  	model.addAttribute("allCards", cardsByPage);
	    model.addAttribute("totalPage", totalPage);
	    model.addAttribute("currentPage", currentPage);

		return "selectCards";
	}

	//添加与修改名片
	@Override
	public String addCard(Card card, HttpServletRequest  request, String act, HttpSession session) throws IllegalStateException, IOException {
		MultipartFile myfile = card.getLogo();
		//如果选择了上传文件，将文件上传到指定的目录static/images
		if(!myfile.isEmpty()) {
			//上传文件路径（生产环境）
			String path = request.getServletContext().getRealPath("/static/images/");
			//获得上传文件原名
			String fileName = myfile.getOriginalFilename();
			//对文件重命名
			String fileNewName = MyUtil.getNewFileName(fileName);
			File filePath = new File(path + File.separator + fileNewName);
			//如果文件目录不存在，创建目录
			if(!filePath.getParentFile().exists()) {
				filePath.getParentFile().mkdirs();
			}
			//将上传文件保存到一个目标文件中
			myfile.transferTo(filePath);
			//将重命名后的图片名存到card对象中，添加时使用
			card.setLogoName(fileNewName);
		}
		if("add".equals(act)) {
			MyUserTable mut = (MyUserTable)session.getAttribute("userLogin");
			card.setUser_id(mut.getId());
			int n = cardDao.addCard(card);
			if(n > 0)//成功
				return "redirect:/card/selectAllCardsByPage?currentPage=1&act=select";
			//失败
			return "addCard";
		}else {//修改
			int n = cardDao.updateCard(card);
			if(n > 0)//成功
				return "redirect:/card/selectAllCardsByPage?currentPage=1&act=updateSelect";
			//失败
			return "updateCard";
		}
	}

	//打开详情与修改页面
	@Override
	public String detail(Model model, int id, String act) {
		CardTable ct = cardDao.selectACard(id);
		model.addAttribute("card", ct);
		if("detail".equals(act)) {
			return "cardDetail";
		}else {
			return "updateCard";
		}
	}

	//删除
	@Override
	public String delete(int id) {
		cardDao.deleteACard(id);
		return "/card/selectAllCardsByPage?currentPage=1&act=deleteSelect";
	}

	//安全退出
	@Override
	public String loginOut(Model model, HttpSession session) {
		session.invalidate();
		model.addAttribute("myUser", new MyUser());
		return "login";
	}

	//打开修改密码页面
	@Override
	public String toUpdatePwd(Model model, HttpSession session) {
		MyUserTable mut = (MyUserTable)session.getAttribute("userLogin");
		model.addAttribute("myuser", mut);
		return "updatePwd";
	}

	// 修改密码
	@Override
	public String updatePwd(MyUser myuser) {
		//将明文变成密文
		myuser.setUpwd(MD5Util.MD5(myuser.getUpwd()));
		cardDao.updatePwd(myuser);
		return "login";
	}
}
