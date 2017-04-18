package QCTeamG.QCApp.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;


@Entity
@Table(name="user_favorites")
public class UserFavoritesEntity {
	
	@Id
    @Column(name="ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
     
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="uid",referencedColumnName="id")
    private UsersEntity uid;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="iid",referencedColumnName="id")
	private ItemsEntity iid;
     
    public ItemsEntity getItem() {
        return iid;
    }
    
    public void setItem(ItemsEntity ie) {
        this.iid = ie;
    }
    
    public UsersEntity getUser() {
        return uid;
    }
    
    public void setUser(UsersEntity ue) {
        this.uid = ue;
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
}

