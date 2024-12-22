package net.java.lms_backend.controller;

import net.java.lms_backend.Service.CourseService;
import net.java.lms_backend.dto.*;
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
    @GetMapping("/available")
    public ResponseEntity<List<Coursedto>> getAvailableCourses() {
        List<Coursedto> courses = courseService.ViewAllCourse();
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/{courseId}/enroll")
    public ResponseEntity<Void> enrollInCourse(@PathVariable Long courseId, @RequestBody Enrollmentdto enrollmentRequest) {
        courseService.enrollStudentInCourse(courseId, enrollmentRequest.getStudentId());
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{courseId}/enrollments")
    public ResponseEntity<List<StudentDTO>> getEnrolledStudents(@PathVariable Long courseId) {
        List<StudentDTO> students = courseService.getEnrolledStudents(courseId);
        return ResponseEntity.ok(students);
    }
    @PostMapping("/{courseId}/lessons/{lessonId}/generate-otp")
    public ResponseEntity<String> generateOtp(@PathVariable Long lessonId) {
        String otp = courseService.generateOtp(lessonId);
        return ResponseEntity.ok(otp);
    }

    @PostMapping("/{courseId}/lessons/{lessonId}/validate-otp")
    public ResponseEntity<Boolean> validateOtp(@PathVariable Long lessonId,@RequestParam String otp){
        boolean attend= courseService.validateOtp(lessonId,otp);
        return ResponseEntity.ok(attend);
    }


    @PostMapping("/{courseId}/add-questions")
    public ResponseEntity<Void> addQuestionsToCourse(@PathVariable Long courseId, @RequestBody List<QuestionDTO> questionDTOs) {
        courseService.addQuestionsToCourse(courseId, questionDTOs);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{courseId}/questions")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByCourseId(@PathVariable Long courseId) {
        List<QuestionDTO> questions = courseService.getQuestionsByCourseId(courseId);
        return ResponseEntity.ok(questions);
    }

}