package net.java.lms_backend.controller;

import net.java.lms_backend.Service.CourseService;
import net.java.lms_backend.dto.Coursedto;
import net.java.lms_backend.entity.Course;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;
    public CourseController(CourseService courseService){
        this.courseService=courseService;
    }
    @PostMapping
    public ResponseEntity<Coursedto> createCourse(@RequestBody Coursedto coursedto) {
        Coursedto newCourse=courseService.CreateCourse(coursedto);
        return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Coursedto>>ViewAllCourse(){
        return ResponseEntity.ok(courseService.ViewAllCourse());
    }
    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<Course>>getCoursesByInstructor(@PathVariable Long instructorId){
        List<Course> courses = courseService.getCoursesByInstructor(instructorId);
        return ResponseEntity.ok(courses);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Coursedto> getCourseById(@PathVariable("id") Long id) {
       Coursedto course=courseService.getCourseById(id);
       return ResponseEntity.ok(course);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }


}
