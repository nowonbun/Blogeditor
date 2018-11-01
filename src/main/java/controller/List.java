package controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import bean.ListBean;
import common.FactoryDao;
import common.IController;
import dao.CategoryDao;
import dao.PostDao;
import model.Category;

@Controller
public class List extends IController {

	private static final long serialVersionUID = 1L;

	@RequestMapping(value = "/DialyList.html")
	protected String dialy(ModelMap modelmap, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		super.initMenu(modelmap, req);
		java.util.List<ListBean> list = getListItem("02");
		modelmap.addAttribute("category_code", "02");
		modelmap.addAttribute("list_title", "개발 일기 리스트");
		modelmap.addAttribute("list_item", list);
		modelmap.addAttribute("list_count", list.size());
		return "list";
	}

	@RequestMapping(value = "/ExperienceList.html")
	protected String experience(ModelMap modelmap, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		super.initMenu(modelmap, req);
		java.util.List<ListBean> list = getListItem("03");
		modelmap.addAttribute("category_code", "03");
		modelmap.addAttribute("list_title", "개발 경험 리스트");
		modelmap.addAttribute("list_item", list);
		modelmap.addAttribute("list_count", list.size());
		return "list";
	}

	@RequestMapping(value = "/KoreanLifeList.html")
	public String koreanLife(ModelMap modelmap, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		super.initMenu(modelmap, req);
		java.util.List<ListBean> list = getListItem("04");
		modelmap.addAttribute("category_code", "04");
		modelmap.addAttribute("list_title", "한국 생활 리스트");
		modelmap.addAttribute("list_item", list);
		modelmap.addAttribute("list_count", list.size());
		return "list";
	}

	@RequestMapping(value = "/JapanLifeList.html")
	public String japanLife(ModelMap modelmap, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		super.initMenu(modelmap, req);
		java.util.List<ListBean> list = getListItem("05");
		modelmap.addAttribute("category_code", "05");
		modelmap.addAttribute("list_title", "일본 생활 리스트");
		modelmap.addAttribute("list_item", list);
		modelmap.addAttribute("list_count", list.size());
		return "list";
	}

	@RequestMapping(value = "/CodingNodeList.html")
	public String codingNode(ModelMap modelmap, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		super.initMenu(modelmap, req);
		java.util.List<ListBean> list = getListItem("06");
		modelmap.addAttribute("category_code", "06");
		modelmap.addAttribute("list_title", "코딩 노트 리스트");
		modelmap.addAttribute("list_item", list);
		modelmap.addAttribute("list_count", list.size());
		return "list";
	}

	@RequestMapping(value = "/FavoritesList.html")
	protected String favorites(ModelMap modelmap, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		super.initMenu(modelmap, req);
		java.util.List<ListBean> list = getListItem("07");
		modelmap.addAttribute("category_code", "07");
		modelmap.addAttribute("list_title", "즐겨 찾기 리스트");
		modelmap.addAttribute("list_item", list);
		modelmap.addAttribute("list_count", list.size());
		return "list";
	}

	private java.util.List<ListBean> getListItem(String categoryCode) {
		java.util.List<ListBean> ret = new ArrayList<>();
		Category category = FactoryDao.getDao(CategoryDao.class).getCategory(categoryCode);
		java.util.List<model.Post> posts = FactoryDao.getDao(PostDao.class).getPostsByCategory(category);
		for (model.Post post : posts) {
			ListBean bean = new ListBean();
			bean.setIdx(Integer.toString(post.getIdx()));
			bean.setTitle(post.getTitle());
			bean.setImage(new String(post.getImage()));
			bean.setSummary(post.getSummary());
			ret.add(bean);
		}
		return ret;
	}
}