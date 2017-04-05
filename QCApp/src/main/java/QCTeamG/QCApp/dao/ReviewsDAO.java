package QCTeamG.QCApp.dao;

import java.util.List;

import QCTeamG.QCApp.entities.ReviewsEntity;
import QCTeamG.QCApp.entities.UserAnswersEntity;

public interface ReviewsDAO {
	
	public Integer createUserAnswer(UserAnswersEntity user_answer);
	
	public Integer createReview(ReviewsEntity user_review);
	
	public UserAnswersEntity getUserAnswerById(int uaid);
	
	public ReviewsEntity getReviewById(int aid);
	
	public List<ReviewsEntity> getReviewsByItem(int iid);
	
	public ReviewsEntity getReviewByReviewText(String content);

}
