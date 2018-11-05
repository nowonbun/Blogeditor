package controller;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.List;

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
import model.Category;

@Controller
public class Post extends IController {

	private static final long serialVersionUID = 1L;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분에 작성된 글...");

	@RequestMapping(value = "/index.html")
	public String index(ModelMap modelmap, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		super.initMenu(modelmap, req);
		String categoryCode = "01";
		String postCode = "-1";
		if (req.getParameter("category") != null) {
			categoryCode = req.getParameter("category");
		}
		Category category = FactoryDao.getDao(CategoryDao.class).getCategory(categoryCode);
		if (category == null) {
			categoryCode = "01";
			category = FactoryDao.getDao(CategoryDao.class).getCategory(categoryCode);
		}
		if (req.getParameter("post") != null) {
			postCode = req.getParameter("post");
		}
		int idx = -1;
		try {
			idx = Integer.parseInt(postCode);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		model.Post post = null;
		if ("01".equals(categoryCode)) {
			List<model.Post> posts = FactoryDao.getDao(PostDao.class).getPostsByCategory(FactoryDao.getDao(CategoryDao.class).getCategory(categoryCode));
			if (posts.size() > 0) {
				post = posts.get(0);
			}
		} else {
			post = FactoryDao.getDao(PostDao.class).getPostsByIdx(idx);
		}
		if (post != null) {
			idx = post.getIdx();
			modelmap.addAttribute("title", post.getTitle());
			modelmap.addAttribute("contents", readFile(post.getFilepath()));
			modelmap.addAttribute("urlkey", post.getGuid());
			modelmap.addAttribute("priority", post.getPriority());
			modelmap.addAttribute("changeflag", post.getChangefreg());
			modelmap.addAttribute("image", new String(post.getImage()));
			modelmap.addAttribute("summary", post.getSummary().replace("<br>", "\n"));
		} else {
			idx = -1;
		}
		if ("01".equals(categoryCode) || idx == -1) {
			modelmap.addAttribute("isPreNextPostView", false);
		} else {
			model.Post pre = FactoryDao.getDao(PostDao.class).getPrePostByIdx(category, idx);
			model.Post next = FactoryDao.getDao(PostDao.class).getNextPostByIdx(category, idx);
			if (pre != null) {
				modelmap.addAttribute("isPrePost", true);
				modelmap.addAttribute("prePostIdx", pre.getIdx());
				modelmap.addAttribute("prePost", pre.getTitle());
				modelmap.addAttribute("prePostDate", sdf.format(pre.getCreatedated()));
			} else {
				modelmap.addAttribute("isPrePost", false);
			}
			if (next != null) {
				modelmap.addAttribute("isNextPost", true);
				modelmap.addAttribute("nextPostIdx", next.getIdx());
				modelmap.addAttribute("nextPost", next.getTitle());
				modelmap.addAttribute("nextPostDate", sdf.format(next.getCreatedated()));
			} else {
				modelmap.addAttribute("isNextPost", false);
			}
			if (pre == null && next == null) {
				modelmap.addAttribute("isPreNextPostView", false);
			} else {
				modelmap.addAttribute("isPreNextPostView", true);
			}
		}
		modelmap.addAttribute("category_code", categoryCode);
		modelmap.addAttribute("post_code", idx);
		modelmap.addAttribute("category_name", FactoryDao.getDao(CategoryDao.class).getCategory(categoryCode).getCategoryName());

		modelmap.addAttribute("isViewRecently", !"01".equals(categoryCode));
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
