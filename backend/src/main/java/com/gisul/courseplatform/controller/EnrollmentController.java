package com.gisul.courseplatform.controller;

import com.gisul.courseplatform.dto.EnrollmentResponse;
import com.gisul.courseplatform.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "*")
public class EnrollmentController {
    
    @Autowired
    private EnrollmentService enrollmentService;
    
    @PostMapping("/enroll/{courseId}")
    public ResponseEntity<?> enrollInCourse(@PathVariable Long courseId, 
                                            Authentication authentication) {
        try {
            Long studentId = (Long) authentication.getCredentials();
            EnrollmentResponse response = enrollmentService.enrollInCourse(courseId, studentId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/enrollments")
    public ResponseEntity<List<EnrollmentResponse>> getMyEnrollments(Authentication authentication) {
        Long studentId = (Long) authentication.getCredentials();
        List<EnrollmentResponse> enrollments = enrollmentService.getStudentEnrollments(studentId);
        return ResponseEntity.ok(enrollments);
    }
    
    @PutMapping("/enrollments/{enrollmentId}/progress")
    public ResponseEntity<?> updateProgress(@PathVariable Long enrollmentId,
                                           @RequestBody Map<String, Integer> body,
                                           Authentication authentication) {
        try {
            Long studentId = (Long) authentication.getCredentials();
            Integer completedLessons = body.get("completedLessons");
            EnrollmentResponse response = enrollmentService.updateProgress(enrollmentId, completedLessons, studentId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
