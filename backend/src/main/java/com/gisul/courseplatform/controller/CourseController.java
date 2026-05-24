package com.gisul.courseplatform.controller;

import com.gisul.courseplatform.dto.CourseRequest;
import com.gisul.courseplatform.dto.CourseResponse;
import com.gisul.courseplatform.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CourseController {
    
    @Autowired
    private CourseService courseService;
    
    // Admin endpoints
    @PostMapping("/admin/courses")
    public ResponseEntity<?> createCourse(@Valid @RequestBody CourseRequest request, 
                                          Authentication authentication) {
        try {
            Long adminId = (Long) authentication.getCredentials();
            CourseResponse response = courseService.createCourse(request, adminId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/admin/courses/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id,
                                          @Valid @RequestBody CourseRequest request,
                                          Authentication authentication) {
        try {
            Long adminId = (Long) authentication.getCredentials();
            CourseResponse response = courseService.updateCourse(id, request, adminId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/admin/courses/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id, 
                                          Authentication authentication) {
        try {
            Long adminId = (Long) authentication.getCredentials();
            courseService.deleteCourse(id, adminId);
            return ResponseEntity.ok("Course deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Public endpoints (accessible to all authenticated users)
    @GetMapping("/courses")
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        List<CourseResponse> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }
    
    @GetMapping("/courses/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        try {
            CourseResponse course = courseService.getCourseById(id);
            return ResponseEntity.ok(course);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/courses/search")
    public ResponseEntity<List<CourseResponse>> searchCourses(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search) {
        List<CourseResponse> courses = courseService.searchCourses(category, search);
        return ResponseEntity.ok(courses);
    }
}
