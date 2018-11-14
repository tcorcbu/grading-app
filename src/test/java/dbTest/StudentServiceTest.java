package dbTest;
import db.StudentService;
import gui.Student;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class StudentServiceTest {
    @Test
    public void testInsert(){
        Student student = new Student("Yize", "Liu", "U44314671", "GRAD");
        int res = StudentService.insertStudent(student);
        System.out.println(res);
    }

    @Test
    public void testGetId(){
        Student student = new Student("Yize", "Liu", "U44314671", "GRAD");
        int res = StudentService.getId(student);
        System.out.println(res);
    }

    @Test
    public void testDrop(){
        Student student = new Student("Yize", "Liu", "U44314671", "GRAD");
        StudentService.dropStudent(student);
    }
}
