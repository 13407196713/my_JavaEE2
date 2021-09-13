package controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 统一异常处理
@ControllerAdvice // 异常统一处理
public class GlobalExceptionHandleController {
	@ExceptionHandler(value=Exception.class)
	public String exceptionHandler(Exception e, Model model) {
		String message = "";
		if (e instanceof NoLoginException) {
			message = "noLogin";
		} else {//未知异常
			message =  "noError";
		}
		model.addAttribute("mymessage",message);
		return "error";
	}
}
