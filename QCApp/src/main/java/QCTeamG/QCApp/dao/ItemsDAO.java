package QCTeamG.QCApp.dao;

import org.hibernate.Session;

import QCTeamG.QCApp.entities.ItemsEntity;

public interface ItemsDAO {
	
	public Integer createItem(ItemsEntity ie);
	
	public ItemsEntity getItemByName(String name);
	
	public void updateItem(ItemsEntity it, Session sesh);

}
