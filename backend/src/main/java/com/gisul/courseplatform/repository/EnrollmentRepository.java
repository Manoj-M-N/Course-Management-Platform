package com.gisul.courseplatform.repository;

import com.gisul.courseplatform.model.Enrollment;
import com.gisul.courseplatform.model.User;
import com.gisul.courseplatform.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudent(User student);
    List<Enrollment> findByStudentId(Long studentId);
    Optional<Enrollment> findByStudentAndCourse(User student, Course course);
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
}
