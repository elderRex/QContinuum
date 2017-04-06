package QCTeamG.QCApp.entities;

import java.sql.Date;

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
@Table(name="user_answers")
public class UserAnswersEntity {
	
	@Id
    @Column(name="ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="rid",referencedColumnName="id")
	private ReviewsEntity rid;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="uid",referencedColumnName="id")
    private UsersEntity uid;
     
    @Column(name="DOES_LIKE")
    private Boolean liked;
     
    public UsersEntity getUid() {
        return uid;
    }
    
    public void setUid(UsersEntity uid) {
        this.uid = uid;
    }
    
    public Boolean getLiked() {
        return liked;
    }
        
    public void setLiked(Boolean rating) {
        this.liked = rating;
    }
   
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    
    public ReviewsEntity getRId() {
        return rid;
    }
    public void setRId(ReviewsEntity rid) {
        this.rid = rid;
    }
    
}

