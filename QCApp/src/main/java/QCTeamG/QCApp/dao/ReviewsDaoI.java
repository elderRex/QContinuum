package QCTeamG.QCApp.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import QCTeamG.QCApp.entities.ReviewsEntity;
import QCTeamG.QCApp.entities.UserAnswersEntity;
import QCTeamG.QCApp.entities.UserFavoritesEntity;

@Repository
@Transactional
public class ReviewsDaoI implements ReviewsDAO {
	
	@Autowired
    private SessionFactory sessionFactory;
	
	public Integer createUserAnswer(UserAnswersEntity user_answer, Session sesh) {
		Integer id = (Integer)sesh.save(user_answer);
		return id;
	}
	
	public Integer createReview(ReviewsEntity user_review, Session sesh) {
		Integer id = (Integer)sesh.save(user_review);
		return id;
	}
	
	public UserAnswersEntity getUserAnswerById(int uaid, Session sesh) {
		  UserAnswersEntity uae = (UserAnswersEntity)sesh.createQuery("from UserAnswersEntity where id = "+uaid).uniqueResult();
		  return uae;
	}
	
	public ReviewsEntity getReviewById(int aid, Session sesh) {
		ReviewsEntity re = (ReviewsEntity)sesh.createQuery("from ReviewsEntity where id = "+aid).uniqueResult();
		  return re;
	}
	
	public List<ReviewsEntity> getReviewsByItem(int iid, Session sesh) {
		List<ReviewsEntity> iel = (List<ReviewsEntity>)sesh.createQuery("from ReviewsEntity re where re.iid = "+iid).list();
		  return iel;
	}
	
	public ReviewsEntity getReviewByReviewText(String content, Session sesh) {
		  try
		  {
			  Query qa = sesh.createQuery("from ReviewsEntity r where r.review_text like :rtext");
			  qa.setString("rtext", content);
			  ReviewsEntity re = (ReviewsEntity)qa.list().get(0);
			  return re;
		  }
		  catch (Exception e)
		  {
		  }
		  return null;

	}
	
	/* Methods for getting and posting user favorites */
	public List<UserFavoritesEntity> getUserFavorites(int uid) {
		Session sesh = sessionFactory.getCurrentSession();
		List<UserFavoritesEntity> ufs = (List<UserFavoritesEntity>)sesh.createQuery("from UserFavoritesEntity where uid = "+uid).list();
		return ufs;
	}
	
	public UserFavoritesEntity getSpecificUserFavorite(int ufid) {
		Session sesh = sessionFactory.getCurrentSession();
		UserFavoritesEntity uf = (UserFavoritesEntity)sesh.createQuery("from UserFavoritesEntity uf where uf.id = "+ufid).uniqueResult();
		return uf;
	}
	
	public Integer createUserFavorite(UserFavoritesEntity ufid, Session sesh) {
		Integer id = (Integer)sesh.save(ufid);
		return id;
	}
	
	public void removeUserFavorite(UserFavoritesEntity ufid, Session sesh) {
		sesh.delete(ufid);
	}

}
