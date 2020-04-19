package Beans.ManagerBean;
import javax.faces.application.FacesMessage;
import  javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import Beans.backingbeans.User;
import com.sun.xml.internal.ws.client.RequestContext;
import db.DbConnection;

import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

@ManagedBean
public class userBean {
private DbConnection db;
    private User user;
    private  boolean Msg;

    public String loginAccount() throws SQLException {

        int id = 0;
        ResultSet resultSet = db.loginUser(user.getEmail(), user.getPassword());
        while (resultSet.next()) {
           // System.out.println(resultSet.getString("name"));
            id = resultSet.getInt("id");

        }


        if ( id != 0) {


            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("uemail", user.getEmail());
            session.setAttribute("id", id);
            session.setMaxInactiveInterval(15 * 60);
            return "auth/WelcomeDashboard.xhtml?faces-redirect=true";
        }

        return null;
    }

    public  boolean isLoggedIn()
    {

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
if(session !=null)
{
  String username = (String)  session.getAttribute("uemail");
 // System.out.println("a"+username);
if(username != null)
{
    return  true;
}
}
return false;
    }

    public String logoutUser()
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

        if(session != null)
        {
            session.invalidate();
        }
        return "/index.xhtml?faces-redirect=true";
    }
    public void registerAccount()
    {
       // System.out.println(user.getName());



         Msg =  db.RegisterUser(user.getName(),user.getEmail(),user.getPassword());

    }

public  boolean isRegistered()
{
    return Msg;
}
    public userBean(){
        user = new User();
        db = new DbConnection();


    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
