package dao;

import javax.persistence.Query;

import common.Dao;
import model.Post;

public class PostDao extends Dao<Post> {

	protected PostDao() {
		super(Post.class);
	}

	public boolean hasUrlKey(String guid) {
		return transaction((em) -> {
			String qy = "SELECT p FROM Post p WHERE u.guid = :guid and p.isdeleted = false";
			Query query = em.createQuery(qy);
			query.setParameter("guid", guid);
			return query.getResultList().size() > 0;
		});
	}
}
