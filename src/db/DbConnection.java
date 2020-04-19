package db;

import java.sql.*;


public class DbConnection {
    private String dbURL = "jdbc:mysql://localhost:3306/JSF";
    private String username = "root";
    private String password = "";
    private Connection connection;
    public DbConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(dbURL,username,password);
            if(connection!=null){
                System.out.println("Success");
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean addCourse(String Course_name,int AddedBy,String fee,String phone,String gender,String date){
        try {
            String sqlQuery = "INSERT INTO course(name,addedBy,fee,phone,gender,date) VALUES(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, Course_name);
            preparedStatement.setInt(2, AddedBy);
            preparedStatement.setString(3, fee);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, gender);
            preparedStatement.setString(6, date);


            int noOfRowsInserted = preparedStatement.executeUpdate();
            if(noOfRowsInserted>0){
                System.out.println(noOfRowsInserted + " course inserted!");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean RegisterUser(String name,String email,String password){
        try {
            String sqlQuery = "INSERT INTO user(name,email,password) VALUES(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);

            int noOfRowsInserted = preparedStatement.executeUpdate();
            if(noOfRowsInserted>0){
                System.out.println(noOfRowsInserted + " rows inserted!");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    public ResultSet loginUser(String email,String password){
        try {
            String sqlQuery = "SELECT * from user where email= ? and password =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet result = preparedStatement.executeQuery();

               return result;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateRecord(int id,String Course_name,int AddedBy,String fee,String phone,String gender,String date){
        try {
            String sqlQuery = "UPDATE course SET name=?,addedBy=?,fee=?,phone=?,gender=?,date=? WHERE course_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, Course_name);
            preparedStatement.setInt(2, AddedBy);
            preparedStatement.setString(3, fee);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, gender);
            preparedStatement.setString(6, date);

            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRecord(int id){
        try {
            String sqlQuery = "DELETE from course WHERE course_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getRecord(int id){
        try {
            String sqlQuery = "SELECT * FROM course where course_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1,id);
            ResultSet result = preparedStatement.executeQuery();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getRecords(){
        try {
            String sqlQuery = "SELECT * FROM course";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sqlQuery);
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
