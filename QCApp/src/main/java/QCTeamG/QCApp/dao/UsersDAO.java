package QCTeamG.QCApp.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;

import QCTeamG.QCApp.entities.ItemsEntity;
import QCTeamG.QCApp.entities.ResetPasswordEntity;
import QCTeamG.QCApp.entities.ReviewsEntity;
import QCTeamG.QCApp.entities.UserRolesEntity;
import QCTeamG.QCApp.entities.UsersEntity;

public interface UsersDAO 
{
	public Integer createUser(UsersEntity user);
	
	public UsersEntity getUserByUsername(String username);
	
	public String getUserNameById(int uid);
	
	public UsersEntity getUserById(int uid);
	
	public Integer getUserIdByEmail(String email);
	
	public void modifyUserProfile(UsersEntity ue);
	
	public List<UsersEntity> getAllUsers();
	
	public UsersEntity getCurrentUser(String user_email);
	
	public void changePassword(String email, String hashedPassword);
	
	public UsersEntity getUserByEmail(String user_email);
	
	public void activateUser(UsersEntity user, Session sesh);
	
	public void deleteUserRole(UserRolesEntity ure);
	
	public void setUserRole(UserRolesEntity ure);

	public List<ReviewsEntity> getUserQuestions(int uid);
	
	public ItemsEntity getItemById(int iid);
	
	public ResetPasswordEntity getResetByToken(String token);
	
	public Timestamp getLatestTimestamp(String userid);
	
	public List<ResetPasswordEntity> getAllUserTimestamps(String userid);
	
	public void updateTimestamp(String rankey);
	
	public void createNewTimestamp(Timestamp time_stamp, ResetPasswordEntity password_reset_entity);
	
	public List<ItemsEntity> getSpecificItemsById(List<Integer> iids, Session sesh);
    
}
