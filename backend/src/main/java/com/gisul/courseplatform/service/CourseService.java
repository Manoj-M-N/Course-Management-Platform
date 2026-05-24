package com.gisul.courseplatform.service;

import com.gisul.courseplatform.dto.CourseRequest;
import com.gisul.courseplatform.dto.CourseResponse;
import com.gisul.courseplatform.model.Course;
import com.gisul.courseplatform.model.User;
import com.gisul.courseplatform.repository.CourseRepository;
import com.gisul.courseplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public CourseResponse createCourse(CourseRequest request, Long adminId) {
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        
        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setCategory(request.getCategory());
        course.setThumbnail(request.getThumbnail());
        course.setTotalLessons(request.getTotalLessons());
        course.setCreatedBy(admin);
        
        course = courseRepository.save(course);
        
        return mapToResponse(course);
    }
    
    public CourseResponse updateCourse(Long courseId, CourseRequest request, Long adminId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        if (!course.getCreatedBy().getId().equals(adminId)) {
            throw new RuntimeException("You are not authorized to update this course");
        }
        
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setCategory(request.getCategory());
        course.setThumbnail(request.getThumbnail());
        course.setTotalLessons(request.getTotalLessons());
        
        course = courseRepository.save(course);
        
        return mapToResponse(course);
    }
    
    public void deleteCourse(Long courseId, Long adminId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        if (!course.getCreatedBy().getId().equals(adminId)) {
            throw new RuntimeException("You are not authorized to delete this course");
        }
        
        courseRepository.delete(course);
    }
    
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public CourseResponse getCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return mapToResponse(course);
    }
    
    public List<CourseResponse> searchCourses(String category, String search) {
        List<Course> courses;
        
        if (category != null && search != null) {
            courses = courseRepository.findByCategoryAndTitleContainingIgnoreCase(category, search);
        } else if (category != null) {
            courses = courseRepository.findByCategory(category);
        } else if (search != null) {
            courses = courseRepository.findByTitleContainingIgnoreCase(search);
        } else {
            courses = courseRepository.findAll();
        }
        
        return courses.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    private CourseResponse mapToResponse(Course course) {
        CourseResponse response = new CourseResponse();
        response.setId(course.getId());
        response.setTitle(course.getTitle());
        response.setDescription(course.getDescription());
        response.setCategory(course.getCategory());
        response.setThumbnail(course.getThumbnail());
        response.setTotalLessons(course.getTotalLessons());
        response.setCreatedByName(course.getCreatedBy().getName());
        response.setCreatedById(course.getCreatedBy().getId());
        response.setCreatedAt(course.getCreatedAt());
        response.setUpdatedAt(course.getUpdatedAt());
        return response;
    }
}
