package com.gisul.courseplatform.service;

import com.gisul.courseplatform.dto.EnrollmentResponse;
import com.gisul.courseplatform.model.Course;
import com.gisul.courseplatform.model.Enrollment;
import com.gisul.courseplatform.model.User;
import com.gisul.courseplatform.repository.CourseRepository;
import com.gisul.courseplatform.repository.EnrollmentRepository;
import com.gisul.courseplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {
    
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public EnrollmentResponse enrollInCourse(Long courseId, Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new RuntimeException("Already enrolled in this course");
        }
        
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setCompletedLessons(0);
        enrollment.setProgressPercentage(0.0);
        
        enrollment = enrollmentRepository.save(enrollment);
        
        return mapToResponse(enrollment);
    }
    
    public List<EnrollmentResponse> getStudentEnrollments(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public EnrollmentResponse updateProgress(Long enrollmentId, Integer completedLessons, Long studentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
        
        if (!enrollment.getStudent().getId().equals(studentId)) {
            throw new RuntimeException("You are not authorized to update this enrollment");
        }
        
        enrollment.setCompletedLessons(completedLessons);
        
        // Calculate progress percentage
        int totalLessons = enrollment.getCourse().getTotalLessons();
        double progress = totalLessons > 0 ? (completedLessons * 100.0) / totalLessons : 0.0;
        enrollment.setProgressPercentage(Math.min(progress, 100.0));
        
        enrollment = enrollmentRepository.save(enrollment);
        
        return mapToResponse(enrollment);
    }
    
    private EnrollmentResponse mapToResponse(Enrollment enrollment) {
        EnrollmentResponse response = new EnrollmentResponse();
        response.setId(enrollment.getId());
        response.setCourseId(enrollment.getCourse().getId());
        response.setCourseTitle(enrollment.getCourse().getTitle());
        response.setCourseDescription(enrollment.getCourse().getDescription());
        response.setCourseCategory(enrollment.getCourse().getCategory());
        response.setCourseThumbnail(enrollment.getCourse().getThumbnail());
        response.setTotalLessons(enrollment.getCourse().getTotalLessons());
        response.setCompletedLessons(enrollment.getCompletedLessons());
        response.setProgressPercentage(enrollment.getProgressPercentage());
        response.setEnrolledAt(enrollment.getEnrolledAt());
        return response;
    }
}
