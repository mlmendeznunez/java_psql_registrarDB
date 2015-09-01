import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Student {
  private int id;
  private String name;
  private String enrollment;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEnrollment() {
    return enrollment;
  }

  public Student(String name, String enrollment) {
    this.name = name;
    this.enrollment = enrollment;
  }

  @Override
    public boolean equals(Object otherStudent) {
    if(!(otherStudent instanceof Student )) {
      return false;
    } else {
      Student newStudent = (Student) otherStudent;
      return this.getName().equals(newStudent.getName()) &&
             this.getEnrollment().equals(newStudent.getEnrollment());
    }
  }

  public static List<Student> all() {
    String sql = "SELECT id, name, enrollment FROM students ORDER BY name ASC";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Student.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students (name, enrollment) VALUES (:name, :enrollment)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("enrollment", this.enrollment)
        .executeUpdate()
        .getKey();
    }
  }

  public static Student find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM students WHERE id=:id";
      Student student = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Student.class);
      return student;
    }
  }

  public void update(String name, String enrollment) {
    this.name = name;
    this.enrollment = enrollment;
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE students SET name=:name, enrollment=:enrollment WHERE id=:id";
        con.createQuery(sql)
          .addParameter("name", name)
          .addParameter("enrollment", enrollment)
          .addParameter("id", id) //why do we need the id here but not in others?
          .executeUpdate();
      }
  }

  public void addCourse(Course course) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO courses_students (course_id, student_id) VALUES (:course_id, :student_id)";
      con.createQuery(sql)
        .addParameter("course_id", course.getId())
        .addParameter("student_id", this.getId())
        .executeUpdate();
    }
  }

  public ArrayList<Course> getCourses() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT course_id FROM courses_students WHERE student_id =:student_id";
      List<Integer> courseIds = con.createQuery(sql)
        .addParameter("student_id", this.getId())
        .executeAndFetch(Integer.class);

        ArrayList<Course> courses = new ArrayList<Course>();

      for(Integer index : courseIds) {
        String courseQuery = "SELECT * FROM courses WHERE id=:index";
        Course course = con.createQuery(courseQuery)
          .addParameter("index", index)
          .executeAndFetchFirst(Course.class);
        courses.add(course);
      }return courses;
    }
  }

}//ends class Student
