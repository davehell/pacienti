package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import play.libs.*;

@Entity
public class User extends Model {
    
    @Required
    @MaxSize(15)
    @MinSize(3)
    @Match(value="^\\w*$", message="Not a valid username")
    public String username;
    
    @Required
//     @MaxSize(15)
//     @MinSize(5)
    public String passwordHash;
    
    @Required
    @MaxSize(100)
    public String name;

    @Required
    //@MaxSize(20)
    @ManyToOne
    public AppModul modul;
    //public String modul;

    public boolean isAdmin;    
   
    public User(String username, String password, String name) {
        this.username = username;
        //this.passwordHash = password;
        this.passwordHash = Codec.hexSHA1(password);
        this.name = name;
        create();
    }
    
    
    public static User connect(String username, String password) {
        //return find("byUsernameAndPasswordHash", username, Codec.hexSHA1(password)).first();
        return find("byUsernameAndPasswordHash", username, password).first();
    }

    public static boolean isUserAdmin(String username) {
        //return find("byUsername", username).<User>first().isAdmin;
        User user = find("byUsername", username).first();
        if(user == null) return false;
        return user.isAdmin;
    }

    public static User getByUsername(String username) {
        return find("byUsername", username).first();
    }

    public String toString()  {
        return name;
    }
    
  
    
}
