package QCTeamG.QCApp.dao;

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
		  ItemsEntity ie = (ItemsEntity)sessionFactory.getCurrentSession().createQuery("from ItemsEntity where name = "+name).uniqueResult();
		  return ie;
	}

}
