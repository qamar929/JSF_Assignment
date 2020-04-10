package Beans.ManagerBean;
import  javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import Beans.backingbeans.User;
import db.DbConnection;

import java.sql.ResultSet;

@ManagedBean
public class userBean {
private DbConnection db;
    private User user;

    public String loginAccount()
    {
        ResultSet resultSet = db.loginUser(user.getEmail(),user.getPassword());

        if(resultSet!=null)
        {
      System.out.println("qamar");
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("uemail", user.getEmail());
            session.setMaxInactiveInterval(15 * 60);
            return "WelcomeDashboard.xhtml";
        }
        return  null;
    }

    public String registerAccount()
    {
        System.out.println(user.getName());


        db.RegisterUser(user.getName(),user.getEmail(),user.getPassword());
        return  null;
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
