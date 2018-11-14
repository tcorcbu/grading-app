package dbTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import db.ClassService;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ClassServiceTest {
    @Test
    public void testInsert(){
        String className = "CS 112 Spring 2018";
        ClassService.insertClass(className);
    }

    @Test
    public void testGetId(){
        Integer res1 = ClassService.getId("CS 591 Fall 2018");
        Integer res2 = ClassService.getId("CS 591 Sprint 2017");
        Integer res3 = ClassService.getId("CS 112 Fall 2018");
        Integer res4 = ClassService.getId("CS");
        assertEquals(new Integer(1),res1);
        assertEquals(new Integer(2), res2);
        assertEquals(new Integer(3), res3);
        assertEquals( null, res4);
    }

    @Test
    public void testCalendar(){
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        String currHour = new SimpleDateFormat("HH").format(new Date());
        System.out.println(currHour);
    }
}


