package net.java.lms_backend.controller;

import net.java.lms_backend.Repositrory.PerformanceRepo;
import net.java.lms_backend.Service.CourseService;
import net.java.lms_backend.dto.*;
import net.java.lms_backend.entity.*;
import net.java.lms_backend.mapper.PerformanceMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CourseController {
    private final CourseService courseService;
    public CourseController(CourseService courseService,PerformanceRepo performanceRepo){

        this.courseService=courseService;
    }
    @PostMapping("/instructor/course/create")
    public ResponseEntity<Coursedto> createCourse(@AuthenticationPrincipal User user, @RequestBody Coursedto coursedto) {
        Coursedto newCourse=courseService.CreateCourse(user,coursedto);
        return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
    }
    @PostMapping("/instructor/lessons/course/{courseId}")
    public ResponseEntity<Lesson> addLessonToCourse(@PathVariable Long courseId, @RequestBody LessonDTO lessonDTO) {
        Lesson lesson = new Lesson();
        lesson.setTitle(lessonDTO.getTitle());
        lesson.setContent(lessonDTO.getContent());
        Lesson savedLesson = courseService.addLessonToCourse(courseId, lesson);
        return new ResponseEntity<>(savedLesson, HttpStatus.CREATED);
    }
    @GetMapping("/instructor/courses")
    public ResponseEntity<List<Coursedto>> getCoursesByInstructor(@AuthenticationPrincipal User user){
        List<Coursedto> courses = courseService.getCoursesByInstructor(user.getId());
        return ResponseEntity.ok(courses);
    }
    @DeleteMapping("/instructor/course/{id}")
    public ResponseEntity<Void>deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/instructor/upload/course/{id}")
    public ResponseEntity<Void> uploadMediaFiles(
            @PathVariable Long id,
            @RequestParam("files") List<MultipartFile> files) {
        courseService.uploadMediaFiles(id, files);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/instructor/enrollments/course/{courseId}")
    public ResponseEntity<List<StudentDTO>> getEnrolledStudents(@PathVariable Long courseId) {
        List<StudentDTO> students = courseService.getEnrolledStudents(courseId);
        return ResponseEntity.ok(students);
    }
    @PostMapping("/instructor/generate-otp/course/{courseId}/lessons/{lessonId}")
    public ResponseEntity<String> generateOtp(@PathVariable Long lessonId) {
        String otp = courseService.generateOtp(lessonId);
        return ResponseEntity.ok(otp);
    }

    @GetMapping("/instructor/performance/course/{courseId}/student/{studentId}")
    public ResponseEntity<Integer> getPerformance(@PathVariable Long courseId, @PathVariable Long studentId) {
        int totalLessonsAttended = courseService.getPerformanceForStudent(studentId, courseId);
        return ResponseEntity.ok(totalLessonsAttended);
    }

    @GetMapping("/instructor/attendance/course/lessons/{lessonId}")
    public ResponseEntity<List<Attendancedto>> getAttendanceForLesson( @PathVariable Long lessonId) {
        List<Attendancedto> attendanceRecords = courseService.getAttendanceForLesson(lessonId);
        return ResponseEntity.ok(attendanceRecords);
    }


    @PostMapping("/instructor/add-questions/course/{courseId}")
    public ResponseEntity<Void> addQuestionsToCourse(@PathVariable Long courseId,
                                                     @RequestBody List<QuestionDTO> questionDTOs) {
        courseService.addQuestionsToCourse(courseId, questionDTOs);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("instructor/course/{courseId}/enrollments/{studentId}")
    public ResponseEntity<Void> deleteStudentFromCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        courseService.deleteStudentFromCourse(courseId, studentId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("instructor/enrollments/{enrollmentId}/confirm")
    public ResponseEntity<Void> confirmEnrollment(@PathVariable Long enrollmentId) {
        courseService.confirmEnrollment(enrollmentId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/student/questions/course/{courseId}")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByCourseId(@PathVariable Long courseId) {
        List<QuestionDTO> questions = courseService.getQuestionsByCourseId(courseId);
        return ResponseEntity.ok(questions);
    }
    @GetMapping("/student/courses")
    public ResponseEntity<List<Coursedto>> getAvailableCourses() {
        List<Coursedto> courses = courseService.ViewAllCourse();
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/student/enroll/course/{courseId}")
    public ResponseEntity<Void> enrollInCourse(@PathVariable Long courseId,
                                               @AuthenticationPrincipal User user) {
        courseService.enrollStudentInCourse(courseId, user.getId());
        return ResponseEntity.ok().build();
    }
    @GetMapping("/student/get/course/{id}")
    public ResponseEntity<Coursedto> getCourseById(@PathVariable("id") Long id) {
        Coursedto course=courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }
    @PostMapping("/student/course/{courseId}/lessons/{lessonId}/validate-otp")
    public ResponseEntity<Boolean> validateOtp(@PathVariable Long lessonId, @RequestParam String otp,
                                               @AuthenticationPrincipal User user) {
        boolean attend = courseService.validateOtp(lessonId, otp, user.getId());
        return ResponseEntity.ok(attend);
    }
    @GetMapping("student/course/{courseId}/media")
    public ResponseEntity<List<MediaFiles>> getMediaFilesByCourseId(@PathVariable Long courseId) {
        List<MediaFiles> mediaFiles = courseService.getMediaFilesByCourseId(courseId);
        return ResponseEntity.ok(mediaFiles);
    }

}