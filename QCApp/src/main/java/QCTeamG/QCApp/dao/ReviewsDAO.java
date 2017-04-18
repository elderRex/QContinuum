package QCTeamG.QCApp.dao;

import java.util.List;

import org.hibernate.Session;

import QCTeamG.QCApp.entities.ReviewsEntity;
import QCTeamG.QCApp.entities.UserAnswersEntity;
import QCTeamG.QCApp.entities.UserFavoritesEntity;

public interface ReviewsDAO {
	
	public Integer createUserAnswer(UserAnswersEntity user_answer, Session sesh);
	
	public Integer createReview(ReviewsEntity user_review, Session sesh);
	
	public UserAnswersEntity getUserAnswerById(int uaid, Session sesh);
	
	public ReviewsEntity getReviewById(int aid, Session sesh);
	
	public List<ReviewsEntity> getReviewsByItem(int iid, Session sesh);
	
	public ReviewsEntity getReviewByReviewText(String content, Session sesh);
	
	public Integer createUserFavorite(UserFavoritesEntity ufid, Session sesh);
	
	public void removeUserFavorite(UserFavoritesEntity ufid, Session sesh);
	
	public UserFavoritesEntity getSpecificUserFavorite(int ufid);
	
	public List<UserFavoritesEntity> getUserFavorites(int uid);

}
