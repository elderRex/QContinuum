package QCTeamG.QCApp.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import QCTeamG.QCApp.entities.ReviewsEntity;
import QCTeamG.QCApp.entities.UserAnswersEntity;

@Repository
@Transactional
public class ReviewsDaoI implements ReviewsDAO {

	@Autowired
    private SessionFactory sessionFactory;
	
	public Integer createUserAnswer(UserAnswersEntity user_answer) {
		Integer id = (Integer) this.sessionFactory.getCurrentSession().save(user_answer);
		return id;
	}
	
	public Integer createReview(ReviewsEntity user_review) {
		Integer id = (Integer) this.sessionFactory.getCurrentSession().save(user_review);
		return id;
	}
	
	public UserAnswersEntity getUserAnswerById(int uaid) {
		  UserAnswersEntity uae = (UserAnswersEntity)sessionFactory.getCurrentSession().createQuery("from UserAnswersEntity where id = "+uaid).uniqueResult();
		  return uae;
	}
	
	public ReviewsEntity getReviewById(int aid) {
		ReviewsEntity re = (ReviewsEntity)sessionFactory.getCurrentSession().createQuery("from ReviewsEntity where id = "+aid).uniqueResult();
		  return re;
	}
	
	public List<ReviewsEntity> getReviewsByItem(int iid) {
		List<ReviewsEntity> iel = (List<ReviewsEntity>)sessionFactory.getCurrentSession().createQuery("from ReviewsEntity re where re.iid = "+iid).list();
		  return iel;
	}
	
	public ReviewsEntity getReviewByReviewText(String content) {
		  try
		  {
			  Query qa = sessionFactory.getCurrentSession().createQuery("from ReviewsEntity r where r.review_text like :rtext");
			  qa.setString("rtext", content);
			  ReviewsEntity re = (ReviewsEntity)qa.list().get(0);
			  return re;
		  }
		  catch (Exception e)
		  {
		  }
		  return null;

	}
	
}
