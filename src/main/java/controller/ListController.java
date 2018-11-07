package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import bean.ListBean;
import bean.ListItemBean;
import common.FactoryDao;
import common.IController;
import dao.CategoryDao;
import dao.PostDao;
import model.Category;
import model.Post;

@Controller
public class ListController extends IController {

	private static final long serialVersionUID = 1L;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분에 작성된 글...");

	private String setList(ModelMap modelmap, HttpServletRequest req, String categoryCode) {
		Category category = FactoryDao.getDao(CategoryDao.class).getCategory(categoryCode);
		super.initMenu(modelmap, categoryCode, req);
		ListBean bean = new ListBean();
		bean.setListItem(getListItem(categoryCode));
		bean.setCategoryCode(categoryCode);
		bean.setListTitle(category.getCategoryName() + " 리스트");
		bean.setListCount(bean.getListItem().size());
		modelmap.addAttribute("listModel", bean);
		return "list";
	}

	@RequestMapping(value = "/DialyList.html")
	protected String dialy(ModelMap modelmap, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		return setList(modelmap, req, "02");
	}

	@RequestMapping(value = "/ExperienceList.html")
	protected String experience(ModelMap modelmap, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		return setList(modelmap, req, "03");
	}

	@RequestMapping(value = "/KoreanLifeList.html")
	public String koreanLife(ModelMap modelmap, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		return setList(modelmap, req, "04");
	}

	@RequestMapping(value = "/JapanLifeList.html")
	public String japanLife(ModelMap modelmap, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		return setList(modelmap, req, "05");
	}

	@RequestMapping(value = "/CodingNoteList.html")
	public String codingNode(ModelMap modelmap, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		return setList(modelmap, req, "06");
	}

	@RequestMapping(value = "/FavoritesList.html")
	protected String favorites(ModelMap modelmap, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		return setList(modelmap, req, "07");
	}

	private List<ListItemBean> getListItem(String categoryCode) {
		List<ListItemBean> ret = new ArrayList<>();
		Category category = FactoryDao.getDao(CategoryDao.class).getCategory(categoryCode);
		List<Post> posts = FactoryDao.getDao(PostDao.class).getPostsByCategory(category);
		for (Post post : posts) {
			if (post.getIsdeleted()) {
				continue;
			}
			ListItemBean bean = new ListItemBean();
			bean.setIdx(post.getIdx());
			bean.setTitle(post.getTitle());
			bean.setImage(new String(post.getImage()));
			bean.setSummary(post.getSummary());
			bean.setDate(sdf.format(post.getCreatedated()));
			ret.add(bean);
		}
		return ret;
	}
}