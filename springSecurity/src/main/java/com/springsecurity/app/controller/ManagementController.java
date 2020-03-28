package com.springsecurity.app.controller;

import java.util.Arrays;
import java.util.List;

import com.springsecurity.app.model.Student;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management/api/v1/students")
public class ManagementController {
    private static final List<Student> students = Arrays.asList(new Student(1, "Aaron Lam"),
            new Student(2, "Maria Gorva"), new Student(3, "Tyler Austin"));

    @GetMapping
    public List<Student> getAllStudents() {
        return students;
    }

    @PostMapping
    public void registerStudent(@RequestBody Student student) {
        System.out.println(student.toString());
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Integer id) {
        System.out.println(id);
    }

    @PutMapping
    public void updateStudent(@RequestBody Student student) {
        System.out.println(student);
    }
}