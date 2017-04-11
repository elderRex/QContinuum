package QCTeamG.QCApp.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import QCTeamG.QCApp.entities.ItemsEntity;
import QCTeamG.QCApp.entities.ResetPasswordEntity;
import QCTeamG.QCApp.entities.ReviewsEntity;
import QCTeamG.QCApp.entities.UserRolesEntity;
import QCTeamG.QCApp.entities.UsersEntity;

@Repository
@Transactional
public class UsersDaoI implements UsersDAO  {

	@Autowired
    private SessionFactory sessionFactory;
	
	public Integer createUser(UsersEntity user) {
		Integer id = (Integer) this.sessionFactory.getCurrentSession().save(user);
		return id;
	}
	
	@Transactional
	public void modifyUserProfile(UsersEntity ue)
	{
		if (ue != null) 
		{
      		try
      		{
      			this.sessionFactory.getCurrentSession().update(ue);
      		}
      		catch (Exception e)
      		{
      			System.out.println("exception" + e);
      		}
		}
	}
	
	public void activateUser(UsersEntity user, Session sesh)
	{
        if (user != null) {
        		this.sessionFactory.getCurrentSession().update(user);

        }
	}

	public String getUserNameById(int uid) {
		  UsersEntity ue = (UsersEntity)sessionFactory.getCurrentSession().createQuery("from UsersEntity where id = "+uid).uniqueResult();
		  return ue.getFirstname() + " " + ue.getLastname();
	}
	
	public UsersEntity getUserById(int uid) {
		  UsersEntity ue = (UsersEntity)sessionFactory.getCurrentSession().createQuery("from UsersEntity where id = "+uid).uniqueResult();
		  return ue;
	}
	
	public Integer getUserIdByEmail(String email) {
		 Query cq = sessionFactory.getCurrentSession().createQuery("select u.id from UsersEntity u where u.email = :email");
		  cq.setParameter("email", email);
		  return (Integer) cq.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<UsersEntity> getAllUsers() {
		return this.sessionFactory.getCurrentSession().createQuery("from UsersEntity").list();
	}
	
	public UsersEntity getCurrentUser(String user_email) {
		  Query cq = sessionFactory.getCurrentSession().createQuery("select u from UsersEntity u where u.email = :email");
		  cq.setParameter("email", user_email);
		  Object ue = cq.uniqueResult();
		return (UsersEntity) ue;
	}
	
	public void changePassword(String email, String hashedPassword)
	{	
 		Query cq = sessionFactory.getCurrentSession().createQuery("update UsersEntity set password=:hashedPassword where email = :email");
		cq.setString("hashedPassword", hashedPassword);
		cq.setString("email", email);
		cq.executeUpdate();
	}
	
	public UsersEntity getUserByEmail(String user_email) {
		  Query cq = sessionFactory.getCurrentSession().createQuery("select u from UsersEntity u where u.email = :email");
		  cq.setParameter("email", user_email);
		  Object ue = cq.uniqueResult();
		return (UsersEntity) ue;
	}
	
	public UsersEntity getUserByUsername(String username) {
		  Query cq = sessionFactory.getCurrentSession().createQuery("select u from UsersEntity u where u.username = :username");
		  Object oe = cq.uniqueResult();
		return (UsersEntity) oe;
	}
	
	public void setUserRole(UserRolesEntity ure) {
		this.sessionFactory.getCurrentSession().save(ure);
	}
	
	public void deleteUserRole(UserRolesEntity ure) {
		this.sessionFactory.getCurrentSession().delete(ure);
	}

	public List<ReviewsEntity> getUserQuestions(int uid) {
		  Query cq = sessionFactory.getCurrentSession().createQuery("select u from ReviewsEntity u ORDER BY rand()").setMaxResults(20);
		  Object oe = cq.list();
		return (List<ReviewsEntity>) oe;
	}
	
	public ItemsEntity getItemById(int iid) {
		 //Query cq = sessionFactory.getCurrentSession().createQuery("select u from ItemsEntity u ORDER BY rand()").setMaxResults(7);
		 Query cq = sessionFactory.getCurrentSession().createQuery("select ie from ItemsEntity ie where ie.id = :itid");
		 cq.setParameter("itid", iid);
		 Object item = cq.uniqueResult();
		 return (ItemsEntity) item;
	}
	
	public List<ItemsEntity> getSpecificItemsById(List<Integer> iids, Session sesh)
	{
		 try
		 {
			 Query cq = sesh.createQuery("select ie from ItemsEntity ie where ie.id in (:list)");
			 cq.setParameterList("list", iids);
			 return (List<ItemsEntity>)cq.list();
		 }
		 catch (Exception e)
		 {
			 System.out.println(e);
		 }
		 return null;
	}
	
	/*
	 * 
	 * Password Reset Methods
	 * 
	 */

	@Transactional
	public void createNewTimestamp(Timestamp time_stamp, ResetPasswordEntity password_reset_entity) {
		try
		{
			this.sessionFactory.getCurrentSession().save(password_reset_entity);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	
	public void updateTimestamp(String rankey)
    {	    		
    		Query qq = sessionFactory.getCurrentSession().createQuery("update PasswordResetEntity set used=:usd where random_key = :rankey");
    		qq.setBoolean("usd", true);
    		qq.setString("rankey", rankey);
    		qq.executeUpdate();
    		
    }
	
	@SuppressWarnings("unchecked")
	public List<ResetPasswordEntity> getAllUserTimestamps(String userid) {
		return this.sessionFactory.getCurrentSession().createQuery("from PasswordResetEntity").list();
	}
	
	
	public Timestamp getLatestTimestamp(String userid) {
		
	ResetPasswordEntity ee = (ResetPasswordEntity)sessionFactory.getCurrentSession().createQuery("from PasswordResetEntity where id = "+userid).uniqueResult();
		  return ee.getTime();
		
	}
	
	 public ResetPasswordEntity getResetByToken(String token) {
		 ResetPasswordEntity ee = (ResetPasswordEntity)sessionFactory.getCurrentSession().createQuery("from ResetPasswordEntity where random_key ='"+token+"'").uniqueResult();
		  return ee;
	 }

}
