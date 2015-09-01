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

     @Test
     public void find_findsStudentInDatabase_true() {
       Student myStudent = new Student("Moop", "morp");
       myStudent.save();
       Student savedStudent = Student.find(myStudent.getId());
       assertTrue(myStudent.equals(savedStudent));
     }

     @Test
     public void update_changesStudentNameAndEnrollmentInDatabase_true() {
       Student myStudent = new Student("molly", "haha");
       myStudent.save();
       String name = "harold";
       String enrollment = "whenever";
       myStudent.update(name, enrollment);
       assertTrue(Student.all().get(0).getName().equals(name));
       assertTrue(Student.all().get(0).getEnrollment().equals(enrollment));
     }

     @Test
     public void addCourse_addsCourseToStudent() {
       Course myCourse = new Course("Name", "coursenum", "description");
       myCourse.save();

       Student myStudent = new Student("Mary Lou", "May 2015");
       myStudent.save();

       myStudent.addCourse(myCourse);
       Course savedCourse = myStudent.getCourses().get(0);
       assertTrue(myCourse.equals(savedCourse));
     }

     @Test
     public void getCourses_returnsAllCourses_ArrayList() {
       Course myCourse = new Course("name", "coursenumb", "what is it about");
       myCourse.save();

       Student myStudent = new Student("Hilary", "April 1906");
       myStudent.save();

       myStudent.addCourse(myCourse);
       List savedCourses = myStudent.getCourses();
       assertEquals(savedCourses.size(), 1);
     }

 }
