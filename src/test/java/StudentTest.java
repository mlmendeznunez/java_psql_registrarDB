import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class StudentTest {

     @Rule
     public DatabaseRule database = new DatabaseRule();

     @Test
     public void all_emptyAtFirst() {
       assertEquals(Student.all().size(), 0);
     }

     @Test
     public void equals_returnsTrueIfStudentsAreTheSame() {
       Student newStudent = new Student("Mary Blue", "9/9/1999");
       Student secondStudent = new Student("Mary Blue", "9/9/1999");
       assertTrue(newStudent.equals(secondStudent));
     }

     @Test
     public void save_savesIntoDatabase_true() {
       Student newStudent = new Student("Mary Blue", "9/9/1999");
       newStudent.save();
       assertTrue(Student.all().get(0).equals(newStudent));
     }

 }
