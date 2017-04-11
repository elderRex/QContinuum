package QCTeamG.QCApp.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="reset_password")
public class ResetPasswordEntity {
	     
		@Id
	    @Column(name="idpassword_reset")
	    @GeneratedValue
	    private Integer id;
	     
	    @Column(name="uid")
	    private Integer uid;
	    
	    @Column(name="reset_time")
	    private Timestamp rtime;
	    
	    @Column(name="random_token")
	    private String random_token;
	    
	    @Column(name="user_email")
	    private String user_email;
	    
	    @Column(name="was_reset")
	    private Boolean was_reset;
	    
	    public Boolean getWasReset() {
	        return was_reset;
	    }
	    
	    public void setWasReset(Boolean wr) {
	        this.was_reset = wr;
	    }
	    
	    public Timestamp getTime() {
	        return rtime;
	    }
	    
	    public void setTime(Timestamp timestamp) {
	        this.rtime = timestamp;
	    }
	    
	    
	    public Integer getId() {
	        return id;
	    }
	    
	    public Integer getUId() {
	        return uid;
	    }
	    
	    public void setUId(Integer uid) {
	        this.uid = uid;
	    }
	    
	    public String getUserEmail() {
	        return user_email;
	    }
	    
	    public void setUserEmail(String useremail) {
	        this.user_email = useremail;
	    }
	    
	    public void setRandomKey(String randomkey) {
	        this.random_token = randomkey;
	    }
	    
	    public String getRandomKey() {
	        return random_token;
	    }
	    
	        
	}

