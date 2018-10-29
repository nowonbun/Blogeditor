package dao;

import java.util.List;

import javax.persistence.Query;

import common.Dao;
import model.Category;
import model.Post;

public class PostDao extends Dao<Post> {

	protected PostDao() {
		super(Post.class);
	}

	public boolean hasUrlKey(String guid) {
		return transaction((em) -> {
			String qy = "SELECT p FROM Post p WHERE p.guid = :guid and p.isdeleted = false";
			Query query = em.createQuery(qy);
			query.setParameter("guid", guid);
			return query.getResultList().size() > 0;
		});
	}

	@SuppressWarnings("unchecked")
	public List<Post> getPostsByCategory(Category category) {
		return transaction((em) -> {
			String qy = "SELECT p FROM Post p WHERE p.category = :category and p.isdeleted = false";
			Query query = em.createQuery(qy);
			query.setParameter("category", category);
			return (List<Post>) query.getResultList();
		});
	}
}
