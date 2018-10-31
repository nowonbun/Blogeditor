package ajax;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import bean.AjaxReturnBean;
import bean.PostBean;
import common.FactoryDao;
import common.IController;
import common.JsonConverter;
import common.PropertyMap;
import common.Util;
import dao.CategoryDao;
import dao.PostDao;
import model.Post;

@Controller
public class PostAjax extends IController {

	private static final long serialVersionUID = 1L;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	@RequestMapping(value = "/insertPost.ajax", produces = "application/text; charset=utf8")
	public void insertPost(ModelMap modelmap, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		int changeFlag = 5;
		int priority = 5;
		PostBean bean = JsonConverter.parseObject(getPostData(req), (obj) -> {
			PostBean ret = new PostBean();
			ret.setCategoryCode(JsonConverter.JsonString(obj, "categoryCode"));
			ret.setTitle(JsonConverter.JsonString(obj, "title"));
			ret.setContents(JsonConverter.JsonString(obj, "contents"));
			ret.setUrlkey(JsonConverter.JsonString(obj, "urlkey"));
			ret.setChangefleg(JsonConverter.JsonString(obj, "changefleg"));
			ret.setPriority(JsonConverter.JsonString(obj, "priority"));
			ret.setImage(new String(JsonConverter.JsonBytes(obj, "image")));
			ret.setSummary(JsonConverter.JsonString(obj, "summary"));
			return ret;
		});
		if (Util.StringIsEmptyOrNull(bean.getCategoryCode())) {
			getPrinter(res).println("categoryCode error");
			return;
		}
		if (Util.StringIsEmptyOrNull(bean.getTitle())) {
			getPrinter(res).println("title error");
			return;
		}
		if (Util.StringIsEmptyOrNull(bean.getContents())) {
			getPrinter(res).println("contents error");
			return;
		}
		if (Util.StringIsEmptyOrNull(bean.getUrlkey()) || FactoryDao.getDao(PostDao.class).hasUrlKey(bean.getUrlkey())) {
			bean.setUrlkey(Util.createGUID());
		}

		if (Util.StringIsEmptyOrNull(bean.getChangefleg())) {
			changeFlag = 5;
		} else {
			try {
				changeFlag = Integer.parseInt(bean.getChangefleg());
			} catch (Exception e) {
				changeFlag = 5;
			}
		}
		if (Util.StringIsEmptyOrNull(bean.getPriority())) {
			priority = 5;
		} else {
			try {
				priority = Integer.parseInt(bean.getPriority());
			} catch (Exception e) {
				priority = 5;
			}
		}
		if (bean.getImage() == null) {
			AjaxReturn(res, AjaxReturnBean.ERROR, "Please choice the image.", -1);
			return;
		}

		Post post = new Post();
		post.setCategory(FactoryDao.getDao(CategoryDao.class).getCategory(bean.getCategoryCode()));
		if (post.getCategory() == null) {
			AjaxReturn(res, AjaxReturnBean.ERROR, "CategoryCode mapping error.", -1);
			return;
		}
		if (Util.StringEquals("01", bean.getCategoryCode())) {
			for (Post p : FactoryDao.getDao(PostDao.class).getPostsByCategory(post.getCategory())) {
				p.setIsdeleted(true);
				FactoryDao.getDao(PostDao.class).update(p);
			}
		}
		post.setTitle(bean.getTitle());
		String filepath = writeFile(PropertyMap.getInstance().getProperty("config", "file_path_root"), bean.getUrlkey(), bean.getContents());
		post.setFilepath(filepath);
		post.setChangefreg(changeFlag);
		post.setPriority(priority);
		post.setLocation(PropertyMap.getInstance().getProperty("config", "web_root") + "/" + bean.getUrlkey() + ".html");
		post.setCreatedated(new Date());
		post.setLastUpdated(new Date());
		post.setGuid(bean.getUrlkey());
		post.setSummary(bean.getSummary());
		post.setIsdeleted(false);
		post.setImage(bean.getImage().getBytes());
		FactoryDao.getDao(PostDao.class).create(post);

		AjaxReturn(res, AjaxReturnBean.SUCCESS, "The post is created.", post.getIdx());
	}

	private void AjaxReturn(HttpServletResponse res, String type, String message, int postCode) {
		AjaxReturnBean bean = new AjaxReturnBean();
		bean.setType(type);
		bean.setMessage(message);
		bean.setPostCode(postCode);
		getPrinter(res).println(JsonConverter.create(bean));
	}

	@RequestMapping(value = "/getPost.ajax")
	public void getPost(ModelMap modelmap, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		PostBean bean = JsonConverter.parseObject(getPostData(req), (obj) -> {
			PostBean ret = new PostBean();
			ret.setCategoryCode(JsonConverter.JsonString(obj, "categoryCode"));
			ret.setIdx(JsonConverter.JsonString(obj, "postCode"));
			return ret;
		});
		if (Util.StringEquals("01", bean.getCategoryCode())) {
			List<Post> posts = FactoryDao.getDao(PostDao.class).getPostsByCategory(FactoryDao.getDao(CategoryDao.class).getCategory(bean.getCategoryCode()));
			if (posts.size() > 0) {
				bean.setIdx(Integer.toString(posts.get(0).getIdx()));
				bean.setTitle(posts.get(0).getTitle());
				bean.setContents(readFile(posts.get(0).getFilepath()));
				bean.setUrlkey(posts.get(0).getGuid());
				bean.setPriority(Integer.toString(posts.get(0).getPriority()));
				bean.setChangefleg(Integer.toString(posts.get(0).getChangefreg()));
				bean.setImage(new String(posts.get(0).getImage()));
			} else {
				bean.setIdx("");
			}
		} else if (!Util.StringIsEmptyOrNull(bean.getIdx())) {
			try {
				int idx = Integer.parseInt(bean.getIdx());
				Post post = FactoryDao.getDao(PostDao.class).getPostsByIdx(idx);
				if (post != null) {
					bean.setIdx(Integer.toString(post.getIdx()));
					bean.setTitle(post.getTitle());
					bean.setContents(readFile(post.getFilepath()));
					bean.setUrlkey(post.getGuid());
					bean.setPriority(Integer.toString(post.getPriority()));
					bean.setChangefleg(Integer.toString(post.getChangefreg()));
					bean.setImage(new String(post.getImage()));
				}
			} catch (Throwable e) {
				e.printStackTrace();
				bean.setIdx("");
			}
		} else {
			bean.setIdx("");
		}
		getPrinter(res).println(JsonConverter.create(bean));
	}

	private String getPostData(HttpServletRequest req) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()))) {
			return br.readLine();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
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

	private String writeFile(String filepath, String urlkey, String contents) {
		File dir = new File(filepath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File file = new File(dir.getAbsolutePath() + File.separator + urlkey + sdf.format(new Date()) + ".post");
		if (file.exists()) {
			return null;
		}
		try (FileOutputStream output = new FileOutputStream(file)) {
			byte[] data = contents.getBytes("UTF-8");
			output.write(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file.getAbsolutePath();
	}
}
