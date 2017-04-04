package QCTeamG.QCApp.dao;

import QCTeamG.QCApp.entities.ItemsEntity;

public interface ItemsDAO {
	
	public Integer createItem(ItemsEntity ie);
	
	public ItemsEntity getItemByName(String name);

}
