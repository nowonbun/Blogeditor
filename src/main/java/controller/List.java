package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import common.IController;

@Controller
public class List extends IController {

	private static final long serialVersionUID = 1L;

	@RequestMapping(value = "/CodingNodeList.html")
	public String codingNode(ModelMap modelmap, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		super.initMenu(modelmap, req);
		modelmap.addAttribute("listTitle", "코딩 노트 리스트");
		return "list";
	}

	@RequestMapping(value = "/DialyList.html")
	protected String dialy(ModelMap modelmap, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		super.initMenu(modelmap, req);
		modelmap.addAttribute("listTitle", "개발 일기 리스트");
		return "list";
	}

	@RequestMapping(value = "/ExperienceList.html")
	protected String experience(ModelMap modelmap, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		super.initMenu(modelmap, req);
		modelmap.addAttribute("listTitle", "개발 경험 리스트");
		return "list";
	}

	@RequestMapping(value = "/FavoritesList.html")
	protected String favorites(ModelMap modelmap, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		super.initMenu(modelmap, req);
		modelmap.addAttribute("listTitle", "즐겨 찾기 리스트");
		return "list";
	}

	@RequestMapping(value = "/JapanLifeList.html")
	public String japanLife(ModelMap modelmap, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		super.initMenu(modelmap, req);
		modelmap.addAttribute("listTitle", "일본 생활 리스트");
		return "list";
	}

	@RequestMapping(value = "/KoreanLifeList.html")
	public String koreanLife(ModelMap modelmap, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		super.initMenu(modelmap, req);
		modelmap.addAttribute("listTitle", "한국 생활 리스트");
		return "list";
	}
}