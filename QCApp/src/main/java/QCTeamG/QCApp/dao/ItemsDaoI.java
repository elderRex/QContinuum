package QCTeamG.QCApp.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import QCTeamG.QCApp.entities.ItemsEntity;

@Repository
@Transactional
public class ItemsDaoI implements ItemsDAO {
	
	@Autowired
    private SessionFactory sessionFactory;
	
	public Integer createItem(ItemsEntity ie) {
		Integer id = (Integer) this.sessionFactory.getCurrentSession().save(ie);
		return id;
	}
	
	public ItemsEntity getItemByName(String name) {
		  
		  try
		  {
			  Query qa = sessionFactory.getCurrentSession().createQuery("from ItemsEntity i where i.name like :iname");
			  qa.setString("iname", name);
			  ItemsEntity ii = (ItemsEntity)qa.list().get(0);
			  return ii;
		  }
		  catch (Exception e)
		  {
		  }
		  return null;
	}
	
	public void updateItem(ItemsEntity it, Session sesh)
	{
        if (it != null) {
        		sesh.update(it);
        }
	}

}
