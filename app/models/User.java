package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import play.libs.*;
import java.util.*;

@Table(
    uniqueConstraints=@UniqueConstraint(columnNames={"modul_id", "parafa"})
)

@Entity
public class User extends Model {
    
    @Required
    @MaxSize(15)
    @MinSize(2)
    @Match(value="^\\w*$", message="Neplatné uživatelské jméno")
    public String username;
    
    @Required
    public String passwordHash;
    
    @Required
    @ManyToOne
    public AppModul modul;

    @Required
    @MaxSize(100)
    public String jmeno;

    @MaxSize(10)
    public String parafa;

    @MaxSize(100)
    public String pozice;

    public boolean isAdmin;
    public boolean isDoctor;
   
    public User(String username, String password, String name) {
        this.username = username;
        //this.passwordHash = password;
        this.passwordHash = Codec.hexSHA1(password);
        this.jmeno = name;
        create();
    }
    
    
    public static User connect(String username, String password) {
        return find("byUsernameAndPasswordHash", username, Codec.hexSHA1(password)).first();
        //return find("byUsernameAndPasswordHash", username, password).first();
    }

    public static boolean isUserAdmin(String username) {
        User user = find("byUsername", username).first();
        if(user == null) return false;
        return user.isAdmin;
    }

    public static boolean isUserDoctor(String username) {
        User user = find("byUsername", username).first();
        if(user == null) return false;
        return user.isDoctor;
    }

    public static User getByUsername(String username) {
        return find("byUsername", username).first();
    }

    public static List<User> getDoctors(AppModul modul) {
        return User.find("modul = ? AND isDoctor = ?", modul, true).fetch();
    }

    public String toString()  {
        return jmeno;
    }
    
  
    
}
