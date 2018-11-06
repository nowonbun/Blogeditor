package dao;

import java.util.List;

import javax.persistence.NoResultException;
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

	public boolean hasUrlKey(String guid, int idx) {
		return transaction((em) -> {
			String qy = "SELECT p FROM Post p WHERE p.guid = :guid and p.isdeleted = false and p.idx != :idx";
			Query query = em.createQuery(qy);
			query.setParameter("guid", guid);
			query.setParameter("idx", idx);
			return query.getResultList().size() > 0;
		});
	}

	@SuppressWarnings("unchecked")
	public List<Post> getPostsByCategory(Category category) {
		return transaction((em) -> {
			String qy = "SELECT p FROM Post p WHERE p.category = :category and p.isdeleted = false order by p.idx desc";
			Query query = em.createQuery(qy);
			query.setParameter("category", category);
			return (List<Post>) query.getResultList();
		});
	}

	public Post getPostsByIdx(int idx) {
		return transaction((em) -> {
			String qy = "SELECT p FROM Post p WHERE p.idx = :idx and p.isdeleted = false";
			Query query = em.createQuery(qy);
			query.setParameter("idx", idx);
			try {
				return (Post) query.getSingleResult();
			} catch (NoResultException e) {
				return null;
			}
		});
	}

	public Post getPrePostByIdx(Category category, int idx) {
		return transaction((em) -> {
			String qy = "SELECT p FROM Post p WHERE p.idx = (SELECT MAX(p1.idx) FROM Post p1 WHERE p1.idx < :idx AND p1.category = :category AND p1.isdeleted = false)";
			Query query = em.createQuery(qy);
			query.setParameter("idx", idx);
			query.setParameter("category", category);
			try {
				return (Post) query.getSingleResult();
			} catch (NoResultException e) {
				return null;
			}
		});
	}

	public Post getNextPostByIdx(Category category, int idx) {
		return transaction((em) -> {
			String qy = "SELECT p FROM Post p WHERE p.idx = (SELECT MIN(p1.idx) FROM Post p1 WHERE p1.idx > :idx AND p1.category = :category AND p1.isdeleted = false)";
			Query query = em.createQuery(qy);
			query.setParameter("idx", idx);
			query.setParameter("category", category);
			try {
				return (Post) query.getSingleResult();
			} catch (NoResultException e) {
				return null;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<Post> getRecently(int count, int idx) {
		return transaction((em) -> {
			String qy = "SELECT p FROM Post p WHERE p.isdeleted = false AND p.idx != :idx ORDER BY p.createdated DESC";
			Query query = em.createQuery(qy);
			query.setParameter("idx", idx);
			query.setMaxResults(count);
			return (List<Post>) query.getResultList();
		});
	}
}
