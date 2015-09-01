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

  public static Course find(int id) {
    try(Connection con = DB.sql2o.open()){
      String sql ="SELECT * FROM courses WHERE id=:id";
      Course course = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Course.class);
      return course;
    }
  }

  public void update(String course_name, String course_num, String description) {
    this.course_name = course_name;
    this.course_num = course_num;
    this.description = description;
    try(Connection con = DB.sql2o.open()){
      String sql = "UPDATE courses SET course_name=:course_name, course_num=:course_num, description=:description WHERE id=:id";
      con.createQuery(sql) //course_name is not this.course_name due to parameters?
        .addParameter("course_name", course_name)
        .addParameter("course_num", course_num)
        .addParameter("description", description)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

    public ArrayList<Student> getStudents() {
      //grabs student ids from a course
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT student_id FROM courses_students WHERE course_id=:course_id";
        List<Integer> studentIds = con.createQuery(sql)
        .addParameter("course_id", this.getId())
        .executeAndFetch(Integer.class);

        //declare empty array to push all students ids that match to the courseid
        ArrayList<Student> students = new ArrayList<Student>();

        //looping through the student index in order to grab all students that match course_id
        for(Integer index : studentIds) { //for index in student Ids
          String studentQuery = "SELECT * FROM students WHERE Id = :index";
          Student student = con.createQuery(studentQuery)
            .addParameter("index", index)
            .executeAndFetchFirst(Student.class);
            students.add(student);
        }return students;
      }

    }



  }//ends class Course
