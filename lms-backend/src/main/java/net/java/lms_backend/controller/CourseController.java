package net.java.lms_backend.controller;

import net.java.lms_backend.Service.CourseService;
import net.java.lms_backend.dto.Coursedto;
import net.java.lms_backend.dto.LessonDTO;
import net.java.lms_backend.entity.Course;
import net.java.lms_backend.entity.Lesson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PostMapping("/{courseId}/lessons")
    public ResponseEntity<Lesson> addLessonToCourse(@PathVariable Long courseId, @RequestBody LessonDTO lessonDTO) {
        Lesson lesson = new Lesson();
        lesson.setTitle(lessonDTO.getTitle());
        lesson.setContent(lessonDTO.getContent());
        Lesson savedLesson = courseService.addLessonToCourse(courseId, lesson);
        return new ResponseEntity<>(savedLesson, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Coursedto>>ViewAllCourse(){

        return ResponseEntity.ok(courseService.ViewAllCourse());
    }
    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<Coursedto>> getCoursesByInstructor(@PathVariable Long instructorId){
        List<Coursedto> courses = courseService.getCoursesByInstructor(instructorId);
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
    @PostMapping("/{id}/upload")
    public ResponseEntity<Void> uploadMediaFiles(
            @PathVariable Long id,
            @RequestParam("files") List<MultipartFile> files) {
        courseService.uploadMediaFiles(id, files);
        return ResponseEntity.ok().build();
    }



}