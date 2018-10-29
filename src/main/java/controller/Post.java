package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import common.IController;

@Controller
public class Post extends IController {

	private static final long serialVersionUID = 1L;

	@RequestMapping(value = "/index.html")
	public String main(ModelMap modelmap, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		super.initMenu(modelmap, req);
		modelmap.addAttribute("category_code", "01");
		modelmap.addAttribute("isViewRecently", false);
		return "post";
	}
}
