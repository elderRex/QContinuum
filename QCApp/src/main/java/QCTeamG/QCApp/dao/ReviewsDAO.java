package QCTeamG.QCApp.dao;

import java.util.List;

import org.hibernate.Session;

import QCTeamG.QCApp.entities.ReviewsEntity;
import QCTeamG.QCApp.entities.UserAnswersEntity;

public interface ReviewsDAO {
	
	public Integer createUserAnswer(UserAnswersEntity user_answer, Session sesh);
	
	public Integer createReview(ReviewsEntity user_review, Session sesh);
	
	public UserAnswersEntity getUserAnswerById(int uaid, Session sesh);
	
	public ReviewsEntity getReviewById(int aid, Session sesh);
	
	public List<ReviewsEntity> getReviewsByItem(int iid, Session sesh);
	
	public ReviewsEntity getReviewByReviewText(String content, Session sesh);

}
