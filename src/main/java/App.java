import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.List;
import java.util.Arrays;
import java.util.Set;

 public class App {
    public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Get page to add student to db
    get("/add-student", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      model.put("template", "templates/newstudent.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Add student to db
    post("/add-student", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String studentname = request.queryParams("name");
      String enrollment = request.queryParams("enrollment");

      Student newStudent = new Student(studentname, enrollment);
      newStudent.save();

      model.put("template", "templates/newstudent.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Get page to add course to db
    get("/add-course", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      model.put("template", "templates/newcourse.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Add course to db
    post("/add-course", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String courseName = request.queryParams("course_name");
      String courseNum = request.queryParams("course_num");
      String description = request.queryParams("description");

      Course newCourse = new Course(courseName, courseNum, description);
      newCourse.save();

      model.put("template", "templates/newcourse.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Get page to display all courses
    get("/all-courses", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      model.put("courses", Course.all());
      model.put("template", "templates/all-courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Get page to display all students
    get("/all-students", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      model.put("students", Student.all()); //hashmap, key
      model.put("template", "templates/all-students.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Get page to update course in db
    get("/courses/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Course course = Course.find(Integer.parseInt(request.params(":id")));

      model.put("course", course);
      model.put("template", "templates/edit-courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Update course in db
    post("/courses/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String courseName = request.queryParams("course_name");
      String courseNum = request.queryParams("course_num");
      String description = request.queryParams("description");

      Course newCourse = Course.find(Integer.parseInt(request.params(":id")));

      newCourse.update(courseName,courseNum,description);//must pass parameters

      response.redirect("/");
      return null;
    });

    get("/students/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Student student = Student.find(Integer.parseInt(request.params(":id")));

      model.put("courses", Course.all());
      model.put("studentcourses", student.getCourses());
      model.put("student", student); //student from id
      model.put("template", "templates/edit-students.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/students/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String enrollment = request.queryParams("enrollment");

      Student newStudent = Student.find(Integer.parseInt(request.params(":id")));
      newStudent.update(name, enrollment);

      response.redirect("/students/:id/edit");
      return null;
    });

    post("students/:id/addcourse", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int stud_id = Integer.parseInt(request.params(":id"));
      Student student = Student.find(Integer.parseInt(request.params(":id")));
      int courseId = Integer.parseInt(request.queryParams("course_id"));
      Course course = Course.find(courseId);

      student.addCourse(course);

      response.redirect("/students/"+ stud_id +"/edit");
      return null;
    });

  }//end of main

}//end of app
