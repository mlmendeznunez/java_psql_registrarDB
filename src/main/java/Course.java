import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;


public class Course {
  private int id;
  private String course_name;
  private String course_num;
  private String description;


  public int getId() {
    return id;
  }

  public String getCourseName() {
    return course_name;
  }

  public String getCourseNum() {
    return course_num;
  }

  public String getDescription() {
    return description;
  }

  public Course(String course_name, String course_num, String description) {
    this.course_name = course_name;
    this.course_num = course_num;
    this.description = description;
  }

  @Override
  public boolean equals(Object otherCourse) {
    if(!(otherCourse instanceof Course )) {
      return false;
    } else {
      Course newCourse = (Course) otherCourse;
      return this.getCourseName().equals(newCourse.getCourseName()) &&
      this.getCourseNum().equals(newCourse.getCourseNum()) &&
      this.getDescription().equals(newCourse.getDescription());
    }
  }

  public static List<Course> all() {
    String sql = "SELECT id, course_name, course_num, description FROM courses ORDER BY course_name ASC";
    try(Connection con = DB.sql2o.open()) {
        return con.createQuery(sql).executeAndFetch(Course.class);
      }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql ="INSERT INTO courses (course_name, course_num, description) values (:course_name, :course_num, :description)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("course_name", this.course_name)
        .addParameter("course_num", this.course_num)
        .addParameter("description", this.description)
        .executeUpdate()
        .getKey();
    }
  }

}
