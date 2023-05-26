package Shared;

import org.mindrot.jbcrypt.BCrypt;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class User implements Serializable {
    private int id;
    private String username;
    private String password;
    private Date date;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String pasrseDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(this.getDate());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", date=" + date +
                '}';
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String hashPassword(){
        return BCrypt.hashpw(this.password, BCrypt.gensalt());
    }
    public void setDateFromString(String string){
        this.date = Date.valueOf(string);
    }
}
