
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
public class MultiTableQueries {
    
 private static Connection connection;
    private static ArrayList<ClassDescription> classDescriptions = new ArrayList<ClassDescription>();
    private static PreparedStatement getAllClassDescriptions;
    private static PreparedStatement getScheduledStudentsByClass;
    private static PreparedStatement getWaitlistedStudentsByClass;
    private static ResultSet resultSet;
    
    
    public static ArrayList<ClassDescription> getAllClassDescriptions(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<ClassDescription> classDescriptions = new ArrayList<ClassDescription>();
        try
        {
            getAllClassDescriptions = connection.prepareStatement(
    "SELECT app.class.coursecode, description, seats " +
    "FROM app.class, app.course " +
    "WHERE semester=? AND app.class.coursecode = app.course.coursecode " +
    "ORDER BY app.class.coursecode"
);
getAllClassDescriptions.setString(1, semester);

            resultSet = getAllClassDescriptions.executeQuery();
            
            while(resultSet.next())
            {
                String courseCode = resultSet.getString("coursecode");
                String description = resultSet.getString("description");
                int seats = resultSet.getInt("seats");
                
                ClassDescription classes = new ClassDescription(courseCode,description,seats);
                
                classDescriptions.add(classes);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return classDescriptions;
    }


public static ArrayList<StudentEntry> getScheduledStudentsByClass(String semester, String courseCode){
        
    //"SELECT app.student.studentID, app.student.Firstname, app.student.Lastname " +
                        //"FROM app.schedule, app.student " +
                       // "WHERE app.schedule.semester = ? AND app.schedule.coursecode = ? AND app.schedule.status = 'Scheduled' AND app.schedule.studentid = app.student.studentid " +
                       // "ORDER BY app.student.lastname, app.student.firstname, app.student.studentid"
               
                       //"select app.schedule.studentid, timestamp, firstname, lastname from app.schedule, " + "app.student where app.schedule.studentid=app.student.studentid and semester = ? and coursecode = ? and status =?
                
    
connection = DBConnection.getConnection();
ArrayList<StudentEntry> scheduledStudents = new ArrayList<StudentEntry>();
    try {
        getScheduledStudentsByClass = connection.prepareStatement("select app.schedule.studentid, timestamp, firstname, lastname from app.schedule, " + "app.student where app.schedule.studentid=app.student.studentid and semester = ? and coursecode = ? and status = ?"
                        
                );
        getScheduledStudentsByClass.setString(1,semester);
        getScheduledStudentsByClass.setString(2,courseCode);
        getScheduledStudentsByClass.setString(3,"Scheduled");
        resultSet = getScheduledStudentsByClass.executeQuery();

        while (resultSet.next()) {
            {
                
                StudentEntry students = new StudentEntry(resultSet.getString("studentID"),resultSet.getString("Firstname"),resultSet.getString("Lastname")); 
                scheduledStudents.add(students);
            }
          
        }
    } catch (SQLException sqlException) {
        sqlException.printStackTrace();
    }

        
        
        return scheduledStudents;
}
public static ArrayList<StudentEntry> getWaitlistedStudentsByClass(String semester, String courseCode){
        //"select app.schedule.studentid, timestamp, firstname, lastname from app.schedule, " + "app.student where app.schedule.studentid=app.student.studentid and semester = ? and coursecode = ? and status = ?"
    // "SELECT app.student.studentID, app.student.Firstname, app.student.Lastname " +
                       // "FROM app.schedule, app.student " +
                        //"WHERE app.schedule.semester = ? AND app.schedule.coursecode = ? AND app.schedule.status = 'Waitlisted' AND app.schedule.studentid = app.student.studentid " +
                        //"ORDER BY app.student.lastname, app.student.firstname, app.student.studentid"
    
connection = DBConnection.getConnection();
ArrayList<StudentEntry> waitlistedStudents = new ArrayList<StudentEntry>();
    try {
        getWaitlistedStudentsByClass = connection.prepareStatement("select app.schedule.studentid, timestamp, firstname, lastname from app.schedule, " + "app.student where app.schedule.studentid=app.student.studentid and semester = ? and coursecode = ? and status = ?"
                       
                
                
                
                
                        );
        getWaitlistedStudentsByClass.setString(1,semester);
        getWaitlistedStudentsByClass.setString(2,courseCode);
        getWaitlistedStudentsByClass.setString(3,"Waitlist");
        resultSet = getWaitlistedStudentsByClass.executeQuery();

        
        
        //
        while (resultSet.next()) {
            
                
                StudentEntry students = new StudentEntry(resultSet.getString("studentid"),resultSet.getString("firstname"),resultSet.getString("lastname")); 
                waitlistedStudents.add(students);
            
          
        }
    } catch (SQLException sqlException) {
        sqlException.printStackTrace();
    }

        
        
        return waitlistedStudents;
}

}