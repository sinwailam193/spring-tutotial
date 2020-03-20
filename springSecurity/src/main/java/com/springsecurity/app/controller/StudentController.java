package com.springsecurity.app.controller;

import java.util.Arrays;
import java.util.List;

import com.springsecurity.app.model.Student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class StudentController {
    private static final List<Student> students = Arrays.asList(new Student(1, "Aaron Lam"),
            new Student(2, "Maria Gorva"), new Student(3, "Tyler Austin"));

    @GetMapping("/student/{id}")
    public Student getStudentById(@PathVariable("id") Integer id) {
        return students.stream().filter(student -> id.equals(student.getId())).findFirst()
                .orElseThrow(() -> new IllegalStateException("Student " + id + " does not exist"));
    }
}