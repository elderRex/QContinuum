package QCTeamG.QCApp.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import QCTeamG.QCApp.entities.ItemsEntity;

public class ItemsDaoI implements ItemsDAO {
	
	@Autowired
    private SessionFactory sessionFactory;
	
	public Integer createItem(ItemsEntity ie) {
		Integer id = (Integer) this.sessionFactory.getCurrentSession().save(ie);
		return id;
	}
	
	public ItemsEntity getItemByName(String name) {
		  ItemsEntity ie = (ItemsEntity)sessionFactory.getCurrentSession().createQuery("from ItemsEntity where name = "+name).uniqueResult();
		  return ie;
	}

}
