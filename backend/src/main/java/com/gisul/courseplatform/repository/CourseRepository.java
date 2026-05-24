package com.gisul.courseplatform.repository;

import com.gisul.courseplatform.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByCategory(String category);
    List<Course> findByTitleContainingIgnoreCase(String title);
    List<Course> findByCategoryAndTitleContainingIgnoreCase(String category, String title);
}
