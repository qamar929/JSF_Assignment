package Beans.ManagerBean;
import Beans.backingbeans.Course;
import Beans.backingbeans.User;
import db.DbConnection;

import  javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ManagedBean
public class courseBean {

    private DbConnection db;
    private Course course;
    private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

  public boolean addCourse()
  {
      System.out.println(course.getCourse_name());
      System.out.println(course.getDate());
      System.out.println(course.getGender());

      HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
      if(session !=null)
      {
          int userid = (Integer)  session.getAttribute("id");
           course.setAddedBy(userid);
          System.out.println(userid);

          try {
              DbConnection connection = new DbConnection();
             boolean x = connection.addCourse(course.getCourse_name(),course.getAddedBy(),course.getFee(),course.getPhone(),course.getGender(),course.getDate());

             System.out.println(x);
             return x;

          }catch (Exception e){
              e.printStackTrace();
          }

      }
      return false;
  }



  public courseBean()
  {
      course = new Course();
      db = new DbConnection();
  }




    public String editCourse(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        int id = Integer.parseInt(params.get("id"));
        DbConnection connection = new DbConnection();
        ResultSet rs = connection.getRecord(id);
        try {
            Course course = new Course();
            while (rs.next()) {

                course.setId(rs.getInt("course_id"));
                course.setCourse_name(rs.getString("name"));
                course.setAddedBy(rs.getInt("addedBy"));
                course.setFee(rs.getString("fee"));
                course.setPhone(rs.getString("phone"));
                course.setGender(rs.getString("gender"));
                course.setDate(rs.getString("date"));

//System.out.println(course.getCourse_name());

                sessionMap.put("editCourse",course);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return "edit.xhtml?faces-redirect=true";
    }
    public String updateCourse(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        Course course = (Course) sessionMap.get("editCourse");
        int id = Integer.parseInt(params.get("id"));
        DbConnection connection = new DbConnection();
        connection.updateRecord(id,course.getCourse_name(),course.getAddedBy(),course.getFee(),course.getPhone(),course.getGender(),course.getDate());
        return "WelcomeDashboard.xhtml?faces-redirect=true";
    }
    public String deleteCourse(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        int id = Integer.parseInt(params.get("id"));
        DbConnection connection = new DbConnection();
       connection.deleteRecord(id);
        return "WelcomeDashboard.xhtml?faces-redirect=true";
    }

    public List<Course> getAllCourses(){
        List<Course> courses = new ArrayList<Course>();
        try {
            DbConnection connection = new DbConnection();
            ResultSet rs = connection.getRecords();
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("course_id"));
               course.setCourse_name(rs.getString("name"));
               course.setAddedBy(rs.getInt("addedBy"));
                course.setFee(rs.getString("fee"));
                course.setPhone(rs.getString("phone"));
                course.setGender(rs.getString("gender"));
                course.setDate(rs.getString("date"));




                courses.add(course);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return courses;
    }















    public DbConnection getDb() {
        return db;
    }

    public void setDb(DbConnection db) {
        this.db = db;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

}
