
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 2021r
 */
public class ScheduleQueries {
    private static Connection connection;
    private static ArrayList<ScheduleEntry> schedules = new ArrayList<ScheduleEntry>();
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getAllStudents;
    private static PreparedStatement getScheduledStudentCount;
    private static PreparedStatement getWaitlistedStudentByClass;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement updateScheduleEntry;
    private static ResultSet resultSet;
    private static int count;
    
    public static void addScheduleEntry(ScheduleEntry entry)
    {
        connection = DBConnection.getConnection();
        try
        {
            addScheduleEntry = connection.prepareStatement("insert into app.schedule (semester,coursecode,studentID,status,timestamp) values (?,?,?,?,?)");
            addScheduleEntry.setString(1, entry.getSemester());
            addScheduleEntry.setString(2, entry.getCourseCode());
            addScheduleEntry.setString(3, entry.getStudentID());
            addScheduleEntry.setString(4, entry.getStatus());
            addScheduleEntry.setTimestamp(5, entry.getTimestamp());


            addScheduleEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedule = new ArrayList<ScheduleEntry>();
        try
        {
            getAllStudents = connection.prepareStatement("select semester, coursecode, studentid, status, timestamp from app.schedule " + "where semester=? and studentID=?");
            getAllStudents.setString(1,semester);
            getAllStudents.setString(2,studentID);
            resultSet = getAllStudents.executeQuery();
            
            while(resultSet.next())
            {
                schedule.add(new ScheduleEntry(
                        resultSet.getString("semester"),
                        resultSet.getString("courseCode"),
                        resultSet.getString("studentID"),
                resultSet.getString("status"),
                resultSet.getTimestamp("timestamp")));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return schedule;
        
    }
    public static int getScheduledStudentCount(String currentSemester,String courseCode){
        int count = 0;
        connection = DBConnection.getConnection();
        
        try
        {
            getScheduledStudentCount = connection.prepareStatement("select count(studentID) from app.schedule where semester = ? and courseCode = ?");
            getScheduledStudentCount.setString(1,currentSemester);
            getScheduledStudentCount.setString(2,courseCode);
             resultSet = getScheduledStudentCount.executeQuery();

        
        if (resultSet.next()) {
            count = resultSet.getInt(1);
        }
    } catch (SQLException sqlException) {
        sqlException.printStackTrace();
    }

    return count;
    }


public static ArrayList<ScheduleEntry> getWaitlistedStudentsByClass(String semester, String courseCode)
{
    connection = DBConnection.getConnection();
    ArrayList<ScheduleEntry> waitlistedSchedules = new ArrayList<ScheduleEntry>();
    try {
        getWaitlistedStudentByClass = connection.prepareStatement("select semester, coursecode, studentid, status, timestamp from app.schedule where semester=? and coursecode=? and status=? order BY timestamp");
        getWaitlistedStudentByClass.setString(1,semester);
        getWaitlistedStudentByClass.setString(2,courseCode);
        getWaitlistedStudentByClass.setString(3,"Waitlist");
        resultSet = getWaitlistedStudentByClass.executeQuery();

        while (resultSet.next()) {
//            if (resultSet.getString("status").equals("Waitlist")){
                
                ScheduleEntry schedule = new ScheduleEntry(resultSet.getString("semester"),resultSet.getString("coursecode"),resultSet.getString("studentid"),resultSet.getString("status"),resultSet.getTimestamp("timestamp")); 
                waitlistedSchedules.add(schedule);
//            }
          
        }
    } catch (SQLException sqlException) {
        sqlException.printStackTrace();
    }
    return waitlistedSchedules;




    
    
}
    
public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode){
    
    connection = DBConnection.getConnection();
        try
        {
            dropStudentScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester=? and studentID=? and coursecode=?");
            dropStudentScheduleByCourse.setString(1, semester);
            dropStudentScheduleByCourse.setString(2, studentID);
            dropStudentScheduleByCourse.setString(3, courseCode);


           
            dropStudentScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    
    
}

public static void dropScheduleByCourse(String semester, String courseCode){
    connection = DBConnection.getConnection();
        try
        {
            dropScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester=? AND coursecode=?");
            dropScheduleByCourse.setString(1, semester);
            dropScheduleByCourse.setString(2, courseCode);
          


           
            dropScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    
}

public static void updateScheduleEntry(ScheduleEntry entry) {
    connection = DBConnection.getConnection();
    try {
        updateScheduleEntry = connection.prepareStatement("UPDATE app.schedule SET status = ? WHERE semester = ? AND coursecode = ? AND studentID = ?");
        updateScheduleEntry.setString(1, "Scheduled");
        updateScheduleEntry.setString(2, entry.getSemester());
        updateScheduleEntry.setString(3, entry.getCourseCode());
        updateScheduleEntry.setString(4, entry.getStudentID());

        updateScheduleEntry.executeUpdate();
    } catch (SQLException sqlException) {
        sqlException.printStackTrace();
    }
}
}