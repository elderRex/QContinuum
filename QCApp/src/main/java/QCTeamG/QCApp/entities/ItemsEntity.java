package QCTeamG.QCApp.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name="items")
public class ItemsEntity {
	
	@Id
    @Column(name="ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
     
    @Column(name="NAME")
    private String name;
    
    @Column(name="TYPE")
    private String type;
    
    @Column(name="SUBJECT")
    private String subject;
    
    @Column(name="DESCRIPTION")
    private String description;
    
    @Transient
    public List<ReviewsEntity> reviews;
 
    @Column(name="WEBSITE")
    public String website;
     
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSubject() {
        return subject;
    }
        
    public void setSubject(String sub) {
        this.subject = sub;
    }
    
    public String getType() {
        return type;
    }
        
    public void setType(String type) {
        this.type = type;
    }
    
    public List<ReviewsEntity> getReviews() {
  	  return reviews;
  	}
  	  
  	public void setReviews(List<ReviewsEntity> lre) {
  	  this.reviews = lre;
  	}  
    
	public String getDescription() {
	  return description;
	}
	  
	public void setDescription(String description) {
	  this.description = description;
	}   

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
   
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    
}
