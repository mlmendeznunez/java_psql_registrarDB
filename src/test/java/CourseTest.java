import java.util.Arrays;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.List;
import java.util.ArrayList;

public class CourseTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Course.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfCoursesAreTheSame() {
    Course firstCourse = new Course("Programming", "C001", "java");
    Course secondCourse = new Course("Programming", "C001", "java");
    assertTrue(firstCourse.equals(secondCourse));
  }

  @Test
  public void save_savesIntoDatabase_true () {
    Course newCourse = new Course ("Programming", "C001", "java");
    newCourse.save();
    assertTrue(Course.all().get(0).equals(newCourse));
  }

  @Test
  public void find_findsCourseInDatabase_true() {
    Course myCourse = new Course("Bubbles", "B001", "water");
    myCourse.save();
    Course savedCourse = Course.find(myCourse.getId());
    assertTrue(myCourse.equals(savedCourse));
  }

  @Test
  public void update_updatesCourseInfoInDatabase_true() {
    Course myCourse = new Course("Bubbles", "B001", "water");
    myCourse.save();
    String name = "Swimming";
    String course_num = "S0001";
    String description = "noodles";
    myCourse.update(name, course_num, description);
    assertTrue(Course.all().get(0).getCourseName().equals(name));
    assertTrue(Course.all().get(0).getCourseNum().equals(course_num));
    assertTrue(Course.all().get(0).getDescription().equals(description));
  }

  @Test
  public void getStudents_returnsAllStudents_List() {
    Student myStudent = new Student ("Bob", "0001");
    myStudent.save();

    assertTrue(myStudent.equals(Student.all().get(0)));
    //assertEquals(Student.all().get(0).equals(myStudent));
  }

  @Test
  public void delete_deletesCourseAndListAssociations() {
    Course myCourse = new Course("Banking", "B001", "Learn to steal money");
    myCourse.save();

    Student myStudent = new Student("Mary Lou", "1984");
    myStudent.save();

    myStudent.addCourse(myCourse);
    myCourse.delete();
    assertEquals(myCourse.getStudents().size(), 0);
  }



















}
