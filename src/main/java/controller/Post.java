package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import common.FactoryDao;
import common.IController;
import dao.CategoryDao;
import dao.PostDao;

@Controller
public class Post extends IController {

	private static final long serialVersionUID = 1L;

	@RequestMapping(value = "/index.html")
	public String index(ModelMap modelmap, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		super.initMenu(modelmap, req);
		String categoryCode = "01";
		String post = "-1";
		if (req.getParameter("category") != null) {
			categoryCode = req.getParameter("category");
		}
		if (FactoryDao.getDao(CategoryDao.class).getCategory(categoryCode) == null) {
			categoryCode = "01";
		}
		if (req.getParameter("post") != null) {
			post = req.getParameter("post");
		}
		int idx = -1;
		try {
			idx = Integer.parseInt(post);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		if (idx > 0 && FactoryDao.getDao(PostDao.class).getPostsByIdx(idx) == null) {
			idx = -1;
		}
		modelmap.addAttribute("category_code", categoryCode);
		modelmap.addAttribute("post_code", idx);
		modelmap.addAttribute("category_name", FactoryDao.getDao(CategoryDao.class).getCategory(categoryCode).getCategoryName());
		modelmap.addAttribute("isPrePostView", !"01".equals(categoryCode));
		modelmap.addAttribute("isViewRecently", !"01".equals(categoryCode));
		return "post";
	}
}
