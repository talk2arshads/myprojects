package com.in28minutes.springboot.service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.in28minutes.springboot.model.Course;
import com.in28minutes.springboot.model.Student;

@Component
public class StudentService {

    private static List<Student> students = new ArrayList<>();

    static {
        // Initialize Data
        final Course course1 = new Course("Course1", "Spring", "10 Steps",
                Arrays.asList("Learn Maven", "Import Project", "First Example", "Second Example"));
        final Course course2 = new Course("Course2", "Spring MVC", "10 Examples",
                Arrays.asList("Learn Maven", "Import Project", "First Example", "Second Example"));
        final Course course3 = new Course("Course3", "Spring Boot", "6K Students",
                Arrays.asList("Learn Maven", "Learn Spring", "Learn Spring MVC", "First Example", "Second Example"));
        final Course course4 = new Course("Course4", "Maven", "Most popular maven course on internet!",
                Arrays.asList("Pom.xml", "Build Life Cycle", "Parent POM", "Importing into Eclipse"));

        final Student akbar = new Student("Student1", "Akbar Mohammed", "Hacker, Sr. Developer Programmer",
                new ArrayList<>(Arrays.asList(course1, course2)));

        final Student hameed = new Student("Student2", "Abdul Hameed Syed", "Teacher, Architect and System Programmer",
                new ArrayList<>(Arrays.asList(course2, course3)));

        final Student saad = new Student("Student3", "Saad Mohammed", "Master, System Programmer and Administrator",
                new ArrayList<>(Arrays.asList(course1, course4)));

        StudentService.students.add(akbar);
        StudentService.students.add(hameed);
        StudentService.students.add(saad);
    }

    public List<Student> retrieveAllStudents() {

        return StudentService.students;
    }

    public Student retrieveStudent(String studentId) {

        for (final Student student : StudentService.students) {
            if (student.getId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    public List<Course> retrieveCourses(String studentId) {

        final Student student = this.retrieveStudent(studentId);

        if (student == null) {
            return null;
        }

        return student.getCourses();
    }

    public String retrieveCourse(String studentId, String courseId) {

        final Student student = this.retrieveStudent(studentId);

        if (student == null) {

            return String.format("Student not found, please try the valid students numbers from the list .!!");
        }

        for (final Course course : student.getCourses()) {
            if (course.getId().equals(courseId)) {
                return course.toString();
            }
        }

        return null;
    }

    private final SecureRandom random = new SecureRandom();

    public Course addCourse(String studentId, Course course) {

        final Student student = this.retrieveStudent(studentId);

        if (student == null) {
            return null;
        }

        final String randomId = new BigInteger(130, this.random).toString(32);
        course.setId(randomId);

        student.getCourses().add(course);

        return course;
    }
}