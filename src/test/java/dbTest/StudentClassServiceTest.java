package dbTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import db.ClassService;
import db.StudentClassService;
import org.junit.jupiter.api.Test;
public class StudentClassServiceTest {
    @Test
    public void testInsert(){
        StudentClassService.insertStudentClass(1,1);
    }

    @Test
    public void testDelete(){
        StudentClassService.deleteStudentClass(1,1);
    }

    @Test
    public void testContains(){
        System.out.println(StudentClassService.containsStudent(1,1));
        System.out.println(StudentClassService.containsStudent(1,2));
    }
}
