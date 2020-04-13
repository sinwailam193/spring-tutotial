package com.springsecurity.app.controller;

import java.util.Arrays;
import java.util.List;

import com.springsecurity.app.model.Student;

import org.springframework.security.access.prepost.PreAuthorize;
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
    private final List<Student> students = Arrays.asList(
        new Student(1, "Aaron Lam"),
        new Student(2, "Maria Gorva"),
        new Student(3, "Tyler Austin")
    );

    @GetMapping
    @PreAuthorize("hasAnyRole(\"ROLE_ADMIN\", \"ROLE_ADMINTRAINEE\")")
    public List<Student> getAllStudents() {
        return students;
    }

    @PostMapping
    @PreAuthorize("hasAuthority(\"student:write\")")
    public void registerStudent(@RequestBody Student student) {
        System.out.println(student.toString());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\"student:write\")")
    public void deleteStudent(@PathVariable Integer id) {
        System.out.println(id);
    }

    @PutMapping
    @PreAuthorize("hasAuthority(\"student:write\")")
    public void updateStudent(@RequestBody Student student) {
        System.out.println(student);
    }
}