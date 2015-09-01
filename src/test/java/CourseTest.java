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
}
