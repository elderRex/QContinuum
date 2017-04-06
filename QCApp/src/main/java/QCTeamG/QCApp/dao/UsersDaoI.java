package QCTeamG.QCApp.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import QCTeamG.QCApp.entities.ItemsEntity;
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
	
	public void modifyUserProfile(UsersEntity ue)
	{
		if (ue != null) 
		{
      		this.sessionFactory.getCurrentSession().update(ue);
		}
	}
	
	public void activateUser(UsersEntity user)
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


}
