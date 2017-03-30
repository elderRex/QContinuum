package QCTeamG.QCApp.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
@Table(name="reviews")
public class ReviewsEntity {
	
	@Id
    @Column(name="ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="uid",referencedColumnName="id")
    private UsersEntity uid;
     
    @Column(name="rating")
    private Integer rating;
    
    @Column(name="REVIEW_TEXT")
    private String review_text;
    
    @ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="iid",referencedColumnName="id")
    private ItemsEntity iid;
 
    @Column(name="DATETIME")
    public Date date;
    
    public UsersEntity getUid() {
        return uid;
    }
    
    public void setUid(UsersEntity ue) {
        this.uid = ue;
    }
    
    public Integer getRating() {
        return rating;
    }
        
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
	public String getText() {
	  return review_text;
	}
	  
	public void setText(String rt) {
	  this.review_text = rt;
	}   

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
   
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    
    public ItemsEntity getIId() {
        return iid;
    }
    public void setIId(ItemsEntity iid) {
        this.iid = iid;
    }
    
}
