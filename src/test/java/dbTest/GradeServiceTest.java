package dbTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import db.ClassService;
import db.GradeService;
import gui.Gradable;
import gui.Student;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GradeServiceTest {
    @Test
    public void testInsert(){
        Gradable gradable = new Gradable();
        gradable.setGradableId(1);
        gradable.setStudentWeight(889);
        gradable.setNote("hello world");
        gradable.setPointsLost(30);
        Student student = new Student();
        student.setSchoolID("U44314671");
        GradeService.insert(gradable,student);
    }
}
