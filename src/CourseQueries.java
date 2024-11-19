/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author 2021r
 */
public class CourseQueries {
 

/**
 *
 * @author acv
 */

    private static Connection connection;
    private static ArrayList<String> courseCode = new ArrayList<String>();
    private static PreparedStatement addCourse;
    private static PreparedStatement getAllCourseCodes;
    private static ResultSet resultSet;
    
    public static void addCourse(CourseEntry course)
    {
        connection = DBConnection.getConnection();
        try
        {
            addCourse = connection.prepareStatement("insert into app.course (coursecode,description) values (?,?)");
            addCourse.setString(1, course.getCourseCode());
            addCourse.setString(2, course.getDescription());
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<String> getAllCourseCodes()
    {
        connection = DBConnection.getConnection();
        ArrayList<String> courseCodesList = new ArrayList<String>();
        try
        {
            getAllCourseCodes = connection.prepareStatement("select coursecode from app.course order by coursecode");
            resultSet = getAllCourseCodes.executeQuery();
            
            while(resultSet.next())
            {
                courseCodesList.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courseCodesList;
        
    }
    
    
}

