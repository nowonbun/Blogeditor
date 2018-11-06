package controller;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import bean.ListItemBean;
import bean.PostBean;
import common.FactoryDao;
import common.IController;
import dao.CategoryDao;
import dao.PostDao;
import model.Category;

@Controller
public class Post extends IController {

	private static final long serialVersionUID = 1L;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분에 작성된 글...");

	@RequestMapping(value = "/index.html")
	public String index(ModelMap modelmap, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		PostBean bean = new PostBean();
		bean.setCategoryCode("01");
		bean.setPostCode("-1");
		if (req.getParameter("category") != null) {
			bean.setCategoryCode(req.getParameter("category"));
		}
		super.initMenu(modelmap, bean.getCategoryCode(), req);
		Category category = FactoryDao.getDao(CategoryDao.class).getCategory(bean.getCategoryCode());
		if (category == null) {
			bean.setCategoryCode("01");
			category = FactoryDao.getDao(CategoryDao.class).getCategory(bean.getCategoryCode());
		}
		if (req.getParameter("post") != null) {
			bean.setPostCode(req.getParameter("post"));
		}

		bean.setIdx(-1);
		try {
			bean.setIdx(Integer.parseInt(bean.getPostCode()));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		model.Post post = null;
		if ("01".equals(bean.getCategoryCode())) {
			List<model.Post> posts = FactoryDao.getDao(PostDao.class).getPostsByCategory(FactoryDao.getDao(CategoryDao.class).getCategory(bean.getCategoryCode()));
			if (posts.size() > 0) {
				post = posts.get(0);
			}
		} else {
			post = FactoryDao.getDao(PostDao.class).getPostsByIdx(bean.getIdx());
		}
		if (post != null) {
			bean.setIdx(post.getIdx());
			bean.setTitle(post.getTitle());
			bean.setContents(readFile(post.getFilepath()));
			bean.setUrlkey(post.getGuid());
			bean.setPriority(Integer.toString(post.getPriority()));
			bean.setChangeflag(Integer.toString(post.getChangefreg()));
			bean.setImage(new String(post.getImage()));
			bean.setSummary(post.getSummary().replace("<br>", "\n"));
		} else {
			bean.setIdx(-1);
		}
		if ("01".equals(bean.getCategoryCode()) || bean.getIdx() == -1) {
			bean.setPreNextPostView(false);
		} else {
			model.Post pre = FactoryDao.getDao(PostDao.class).getPrePostByIdx(category, bean.getIdx());
			model.Post next = FactoryDao.getDao(PostDao.class).getNextPostByIdx(category, bean.getIdx());
			if (pre != null) {
				bean.setPrePost(true);
				bean.setPrePostIdx(pre.getIdx());
				bean.setPrePost(pre.getTitle());
				bean.setPrePostDate(sdf.format(pre.getCreatedated()));
			} else {
				bean.setPrePost(false);
			}
			if (next != null) {
				bean.setNextPost(true);
				bean.setNextPostIdx(next.getIdx());
				bean.setNextPost(next.getTitle());
				bean.setNextPostDate(sdf.format(next.getCreatedated()));
			} else {
				bean.setNextPost(false);
			}
			if (pre == null && next == null) {
				bean.setPreNextPostView(false);
			} else {
				bean.setPreNextPostView(true);
			}
		}
		bean.setRecentlyList(new ArrayList<>());
		for (model.Post item : FactoryDao.getDao(PostDao.class).getRecently(5, bean.getIdx())) {
			ListItemBean sub = new ListItemBean();
			sub.setIdx(item.getIdx());
			sub.setTitle("[" + item.getCategory().getCategoryName() + "] " + item.getTitle());
			sub.setDate(sdf.format(item.getCreatedated()));
			sub.setCategoryCode(item.getCategory().getCategoryCode());
			bean.getRecentlyList().add(sub);
		}
		bean.setViewRecently(bean.getRecentlyList().size() > 0);
		modelmap.addAttribute("postModel", bean);
		return "post";
	}

	private String readFile(String filepath) {
		File file = new File(filepath);
		if (!file.exists()) {
			return null;
		}
		try (FileInputStream input = new FileInputStream(file)) {
			byte[] data = new byte[(int) file.length()];
			input.read(data);
			return new String(data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
