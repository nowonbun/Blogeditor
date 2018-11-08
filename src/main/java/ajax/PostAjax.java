package ajax;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
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
			ret.setChangeflag(JsonConverter.JsonString(obj, "changefleg"));
			ret.setPriority(JsonConverter.JsonString(obj, "priority"));
			ret.setImage(new String(JsonConverter.JsonBytes(obj, "image")));
			ret.setSummary(JsonConverter.JsonString(obj, "summary"));
			ret.setImageComment(JsonConverter.JsonString(obj, "imageComment"));
			return ret;
		});
		if (Util.StringIsEmptyOrNull(bean.getCategoryCode())) {
			AjaxReturn(res, AjaxReturnBean.ERROR, "categoryCode error", -1);
			return;
		}
		if (Util.StringIsEmptyOrNull(bean.getTitle())) {
			AjaxReturn(res, AjaxReturnBean.ERROR, "title error", -1);
			return;
		}
		if (Util.StringIsEmptyOrNull(bean.getContents())) {
			AjaxReturn(res, AjaxReturnBean.ERROR, "contents error", -1);
			return;
		}
		if (Util.StringIsEmptyOrNull(bean.getUrlkey()) || FactoryDao.getDao(PostDao.class).hasUrlKey(bean.getUrlkey())) {
			bean.setUrlkey(Util.createGUID());
		}

		if (Util.StringIsEmptyOrNull(bean.getChangeflag())) {
			changeFlag = 5;
		} else {
			try {
				changeFlag = Integer.parseInt(bean.getChangeflag());
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
		post.setLocation(post.getCategory().getSubdir() + bean.getUrlkey() + ".html");
		post.setCreatedated(new Date());
		post.setLastUpdated(new Date());
		post.setGuid(bean.getUrlkey());
		post.setSummary(bean.getSummary());
		post.setIsdeleted(false);
		post.setImage(bean.getImage().getBytes());
		post.setImageComment(bean.getImageComment());
		FactoryDao.getDao(PostDao.class).create(post);

		AjaxReturn(res, AjaxReturnBean.SUCCESS, "The post is created.", post.getIdx());
	}

	@RequestMapping(value = "/modifyPost.ajax", produces = "application/text; charset=utf8")
	public void modifyPost(ModelMap modelmap, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		int changeFlag = 5;
		int priority = 5;
		PostBean bean = JsonConverter.parseObject(getPostData(req), (obj) -> {
			PostBean ret = new PostBean();
			ret.setPostCode(JsonConverter.JsonString(obj, "idx"));
			ret.setCategoryCode(JsonConverter.JsonString(obj, "categoryCode"));
			ret.setTitle(JsonConverter.JsonString(obj, "title"));
			ret.setContents(JsonConverter.JsonString(obj, "contents"));
			ret.setUrlkey(JsonConverter.JsonString(obj, "urlkey"));
			ret.setChangeflag(JsonConverter.JsonString(obj, "changefleg"));
			ret.setPriority(JsonConverter.JsonString(obj, "priority"));
			ret.setImage(new String(JsonConverter.JsonBytes(obj, "image")));
			ret.setSummary(JsonConverter.JsonString(obj, "summary"));
			ret.setImageComment(JsonConverter.JsonString(obj, "imageComment"));
			return ret;
		});

		if (Util.StringIsEmptyOrNull(bean.getPostCode())) {
			AjaxReturn(res, AjaxReturnBean.ERROR, "post code error", -1);
			return;
		}
		try {
			bean.setIdx(Integer.parseInt(bean.getPostCode()));
		} catch (Exception e) {
			AjaxReturn(res, AjaxReturnBean.ERROR, "post code error", -1);
			return;
		}
		Post post = FactoryDao.getDao(PostDao.class).getPostsByIdx(bean.getIdx());
		if (post == null) {
			AjaxReturn(res, AjaxReturnBean.ERROR, "post code error", -1);
			return;
		}

		if (Util.StringIsEmptyOrNull(bean.getCategoryCode())) {
			AjaxReturn(res, AjaxReturnBean.ERROR, "categoryCode error", -1);
			return;
		}
		if (Util.StringIsEmptyOrNull(bean.getTitle())) {
			AjaxReturn(res, AjaxReturnBean.ERROR, "title error", -1);
			return;
		}
		if (Util.StringIsEmptyOrNull(bean.getContents())) {
			AjaxReturn(res, AjaxReturnBean.ERROR, "contents error", -1);
			return;
		}
		if (Util.StringIsEmptyOrNull(bean.getUrlkey()) || FactoryDao.getDao(PostDao.class).hasUrlKey(bean.getUrlkey(), bean.getIdx())) {
			bean.setUrlkey(Util.createGUID());
		}

		if (Util.StringIsEmptyOrNull(bean.getChangeflag())) {
			changeFlag = 5;
		} else {
			try {
				changeFlag = Integer.parseInt(bean.getChangeflag());
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

		post.setCategory(FactoryDao.getDao(CategoryDao.class).getCategory(bean.getCategoryCode()));
		if (post.getCategory() == null) {
			AjaxReturn(res, AjaxReturnBean.ERROR, "CategoryCode mapping error.", -1);
			return;
		}
		if (Util.StringEquals("01", bean.getCategoryCode())) {
			for (Post p : FactoryDao.getDao(PostDao.class).getPostsByCategory(post.getCategory())) {
				if (p.getIdx() == post.getIdx()) {
					continue;
				}
				p.setIsdeleted(true);
				FactoryDao.getDao(PostDao.class).update(p);
			}
		}
		post.setTitle(bean.getTitle());
		String filepath = writeFile(PropertyMap.getInstance().getProperty("config", "file_path_root"), bean.getUrlkey(), bean.getContents());
		post.setFilepath(filepath);
		post.setChangefreg(changeFlag);
		post.setPriority(priority);
		post.setLocation(post.getCategory().getSubdir() + bean.getUrlkey() + ".html");
		post.setLastUpdated(new Date());
		post.setGuid(bean.getUrlkey());
		post.setSummary(bean.getSummary());
		post.setIsdeleted(false);
		post.setImage(bean.getImage().getBytes());
		post.setImageComment(bean.getImageComment());
		FactoryDao.getDao(PostDao.class).update(post);

		AjaxReturn(res, AjaxReturnBean.SUCCESS, "The post is modified.", post.getIdx());
	}

	@RequestMapping(value = "/deletePost.ajax", produces = "application/text; charset=utf8")
	public void deletePost(ModelMap modelmap, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		PostBean bean = JsonConverter.parseObject(getPostData(req), (obj) -> {
			PostBean ret = new PostBean();
			ret.setCategoryCode(JsonConverter.JsonString(obj, "categoryCode"));
			ret.setPostCode(JsonConverter.JsonString(obj, "idx"));
			return ret;
		});
		if (Util.StringIsEmptyOrNull(bean.getCategoryCode())) {
			AjaxReturn(res, AjaxReturnBean.ERROR, "category error", -1);
			return;
		}
		if (Util.StringIsEmptyOrNull(bean.getPostCode())) {
			AjaxReturn(res, AjaxReturnBean.ERROR, "idx error", -1);
			return;
		}
		try {
			bean.setIdx(Integer.parseInt(bean.getPostCode()));
		} catch (Throwable e) {
			AjaxReturn(res, AjaxReturnBean.ERROR, "idx error", -1);
			return;
		}

		Post post = FactoryDao.getDao(PostDao.class).getPostsByIdx(bean.getIdx());
		if (post == null) {
			AjaxReturn(res, AjaxReturnBean.ERROR, "idx error", -1);
			return;
		}
		if (!Util.StringEquals(bean.getCategoryCode(), post.getCategory().getCategoryCode())) {
			AjaxReturn(res, AjaxReturnBean.ERROR, "category error", -1);
			return;
		}

		post.setIsdeleted(true);
		post.setLastUpdated(new Date());
		FactoryDao.getDao(PostDao.class).update(post);

		AjaxReturn(res, AjaxReturnBean.SUCCESS, "The post is deleted.", post.getIdx());
	}

	private void AjaxReturn(HttpServletResponse res, String type, String message, int postCode) {
		AjaxReturnBean bean = new AjaxReturnBean();
		bean.setType(type);
		bean.setMessage(message);
		bean.setPostCode(postCode);
		getPrinter(res).println(JsonConverter.create(bean));
	}

	private String getPostData(HttpServletRequest req) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()))) {
			return br.readLine();
		} catch (Throwable e) {
			throw new RuntimeException(e);
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
