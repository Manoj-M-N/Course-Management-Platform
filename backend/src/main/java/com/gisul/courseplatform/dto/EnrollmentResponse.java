package com.gisul.courseplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResponse {
    private Long id;
    private Long courseId;
    private String courseTitle;
    private String courseDescription;
    private String courseCategory;
    private String courseThumbnail;
    private Integer totalLessons;
    private Integer completedLessons;
    private Double progressPercentage;
    private LocalDateTime enrolledAt;
}
